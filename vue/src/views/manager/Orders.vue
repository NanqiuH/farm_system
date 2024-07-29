<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.orderNo" style="width: 300px; margin-right: 10px"
                placeholder="Please search by order number"></el-input>
      <el-button type="primary" @click="load">Search</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">Add</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="Order Number" prop="orderNo"></el-table-column>
        <el-table-column label="Products Name" prop="goodsName"></el-table-column>
        <el-table-column label="Products Picture" prop="goodsImg">
          <template #default="scope">
            <el-image
                style="width: 100px; height: 100px; border-radius: 5px;"
                :src="scope.row.goodsImg"
                :preview-src-list="[scope.row.goodsImg]"
                preview-teleported
            />
          </template>
        </el-table-column>
        <el-table-column label="Purchase Number" prop="num"></el-table-column>
        <el-table-column label="Orderer" prop="userName"></el-table-column>
        <el-table-column label="Status" prop="status">
          <template #default="scope">
            <el-tag v-if="scope.row.status === 'Canceled'" type="danger">Already canceled</el-tag>
            <el-tag v-if="scope.row.status === 'Awaiting payment'" type="warning">Awaiting payment</el-tag>
            <el-tag v-if="scope.row.status === 'Awaiting shipment'" type="primary">Awaiting shipment</el-tag>
            <el-tag v-if="scope.row.status === 'On the way'" type="primary">On the way</el-tag>
            <el-tag v-if="scope.row.status === 'Order received'" type="success">Order received</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Time" prop="time"></el-table-column>
        <el-table-column label="Manipulate" width="200">
          <template #default="scope">
            <el-button v-if="data.user.role === 'USER' && scope.row.status ==='Awaiting payment'" type="primary"
                       @click="changeStatus(scope.row, 'Awaiting shipment')">Pay
            </el-button>
            <el-button v-if="data.user.role === 'USER' && scope.row.status ==='Awaiting payment'" type="danger"
                       @click="changeStatus(scope.row, 'Canceled')">Cancel
            </el-button>
            <el-button v-if="data.user.role === 'ADMIN' && scope.row.status ==='Awaiting shipment'" type="primary"
                       @click="changeStatus(scope.row, 'On the way')">Ship
            </el-button>
            <el-button v-if="data.user.role === 'USER' && scope.row.status ==='On the way'" type="success"
                       @click="changeStatus(scope.row, 'Order received')">Order received
            </el-button>
            <el-button v-if="data.user.role === 'ADMIN'" type="danger" @click="handleDelete(scope.row.id)">Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card" v-if="data.total">
      <el-pagination background layout="prev, pager, next" v-model:page-size="data.pageSize"
                     v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  orderNo: null
})

const load = () => {
  request.get('/orders/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      orderNo: data.orderNo
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

const changeStatus = (row, status) => {
  row.status = status
  request.put('/orders/update', row).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('The operation is successful.')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleDelete = (id) => {
  ElMessageBox.confirm('The data cannot be recovered after deletion, are you sure about the deletion?',
      'Delete', {type: 'warning'}).then(res => {
    request.delete('/orders/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('The operation is successful.')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
  })
}

const reset = () => {
  data.orderNo = null
  load()
}

const handleImgSuccess = (res) => {
  data.form.avatar = res.data
}

load()
</script>