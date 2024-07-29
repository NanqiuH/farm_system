<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.goodsName" style="width: 300px; margin-right: 10px"
                placeholder="Please search by goods Name"></el-input>
      <el-button type="primary" @click="load">search</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">Add</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="Name" prop="goodsName"></el-table-column>
        <el-table-column label="Number" prop="num"></el-table-column>
        <el-table-column label="Channel" prop="channel"></el-table-column>
        <el-table-column label="Date" prop="date"></el-table-column>
        <el-table-column label="Comment" prop="comment"></el-table-column>
        <el-table-column label="Manipulate" header-align="center" width="200">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">Edit</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.id)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination background layout="prev, pager, next" v-model:page-size="data.pageSize"
                     v-model:current-page="data.pageNum" :total="data.total"/>
    </div>

    <el-dialog title="Purchase Information" width="40%" v-model="data.formVisible" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="Goods Name" prop="goodsName">
          <el-select v-model="data.form.goodsId" placeholder="Select the goods" style="width: 100%">
            <el-option
                v-for="item in data.goodsList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Number" prop="num">
          <el-input-number :min="1" v-model="data.form.num"/>
        </el-form-item>
        <el-form-item label="Channel" prop="channel">
          <el-input v-model="data.form.channel" placeholder="Please enter your purchase channel"/>
        </el-form-item>
        <el-form-item label="Date" prop="date">
          <el-date-picker type="date" style="width: 100%;" v-model="data.form.date"
                          placeholder="Please select your purchase date"
                          format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
        </el-form-item>
        <el-form-item label="Comment" prop="comment">
          <el-input v-model="data.form.comment" placeholder="Please enter your comment"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">Cancel</el-button>
          <el-button type="primary" @click="save">Save</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  formVisible: false,
  form: {},
  tableData: [],
  goodsName: null,
  goodsList: []
})

request.get('/goods/selectAll').then(res => {
  data.goodsList = res.data
})

const load = () => {
  request.get('/goodsStock/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      goodsName: data.goodsName
    }
  }).then(res => {
    data.tableData = res.data?.list
    data.total = res.data?.total
  })
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const add = () => {
  request.post('/goodsStock/add', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('The operation is successful.')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('/goodsStock/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success('The operation is successful.')
      data.formVisible = false
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const handleDelete = (id) => {
  ElMessageBox.confirm('The data cannot be recovered after deletion, are you sure about the deletion?',
      'Delete', {type: 'warning'}).then(res => {
    request.delete('/goodsStock/delete/' + id).then(res => {
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
  data.goodsName = null
  load()
}

const handleImgSuccess = (res) => {
  data.form.avatar = res.data
}

load()
</script>