<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.name" style="width: 300px; margin-right: 10px"
                placeholder="Please search by name"></el-input>
      <el-button type="primary" @click="load">Search</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">Add</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="Username" prop="username"></el-table-column>
        <el-table-column label="Password" prop="password"></el-table-column>
        <el-table-column label="Name" prop="name"></el-table-column>
        <el-table-column label="Avatar">
          <template #default="scope">
            <el-image :src="scope.row.avatar" style="width: 40px; height: 40px; border-radius: 50%"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="Role" prop="role">
          <template #default="scope">
            <span v-if="scope.row.role === 'USER'">USER</span>
          </template>
        </el-table-column>
        <el-table-column label="Sex" prop="sex"></el-table-column>
        <el-table-column label="Phone" prop="phone"></el-table-column>
        <el-table-column label="Email" prop="email"></el-table-column>
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

    <el-dialog title="Information" width="40%" v-model="data.formVisible" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="Username" prop="username">
          <el-input v-model="data.form.username" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="data.form.password" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="data.form.name" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Avatar" prop="avatar">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">Upload Image</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="Sex" prop="sex">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="Male"></el-radio>
            <el-radio label="Female"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="data.form.phone" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Email" prop="Email">
          <el-input v-model="data.form.email" autocomplete="off"/>
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
  name: null
})

const load = () => {
  request.get('/user/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
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
  request.post('/user/add', data.form).then(res => {
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
  request.put('/user/update', data.form).then(res => {
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
    request.delete('/user/delete/' + id).then(res => {
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
  data.name = null
  load()
}

const handleImgSuccess = (res) => {
  data.form.avatar = res.data
}

load()
</script>