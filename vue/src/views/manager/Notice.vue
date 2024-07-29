<template>
  <div>

    <div class="card" style="margin-bottom: 5px;">
      <el-input v-model="data.title" style="width: 300px; margin-right: 10px"
                placeholder="Please search by title"></el-input>
      <el-button type="primary" @click="load">Search</el-button>
      <el-button type="info" style="margin: 0 10px" @click="reset">Reset</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="handleAdd">Add</el-button>
      </div>
      <el-table :data="data.tableData" stripe>
        <el-table-column label="Title" prop="title"></el-table-column>
        <el-table-column label="Content" prop="content"></el-table-column>
        <el-table-column label="Time" prop="time"></el-table-column>
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

    <el-dialog title="System Announcement" width="40%" v-model="data.formVisible" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="data.form" label-width="100px" style="padding-right: 50px">
        <el-form-item label="Title" prop="title">
          <el-input v-model="data.form.title" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="Content" prop="content">
          <el-input v-model="data.form.content" autocomplete="off"/>
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
  title: null
})

const load = () => {
  request.get('/notice/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title
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
  request.post('/notice/add', data.form).then(res => {
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
  request.put('/notice/update', data.form).then(res => {
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
    request.delete('/notice/delete/' + id).then(res => {
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
  data.title = null
  load()
}

const handleImgSuccess = (res) => {
  data.form.avatar = res.data
}

load()
</script>