<template>
  <div style="padding-bottom: 50px;">
    <div class="card" style="margin-bottom: 10px;">
      <el-button :class="{ 'active' :data.activeCategoryId === null}" @click="loadCategoryGoods(null)">All</el-button>
      <el-button :class="{ 'active' :data.activeCategoryId === item.id}" @click="loadCategoryGoods(item.id)"
                 v-for="item in data.categoryList" :key="item.id">{{ item.name }}
      </el-button>
    </div>
    <div style="margin-bottom: 10px;">
      <el-input size="large" style="width: 300px; margin-right: 5px;" v-model="data.name"
                placeholder="Please enter the products name"></el-input>
      <el-button type="primary" @click="load">Search</el-button>
    </div>

    <el-row :gutter="10" v-if="data.total > 0">
      <el-col :span="6" v-for="item in data.goodsList" :key="item.id">
        <div class="card">
          <img :src="item.img" alt="" style="width: 100%; height: 280px;">
          <div style="font-size: 18px; margin-bottom: 5px; color: #333;">{{ item.name }}</div>
          <el-tooltip v-if="item.descr.length > 80" :content="item.descr" effect="light" placement="bottom">
            <div class="line2" style="margin: 5px 0; color: #666; font-size: 14px; height: 20px;">{{ item.descr }}</div>
          </el-tooltip>
          <div v-else class="line2" style="margin: 5px 0; color: #666; font-size: 14px; height: 20px;">{{
              item.descr
            }}
          </div>
          <div style="margin: 5px 0;">
            <el-tag type="success" style="font-size: 14px;">{{ item.specials }}</el-tag>
          </div>
          <div style="margin: 10px 0; display: flex; align-items: center;">
            <div style="flex: 1">
              <strong style="font-size: 18px;">${{ item.price }}/{{ item.unit }}</strong>
            </div>
            <div style="flex: 1; color: #666; text-align: left;">
              Stock: {{ item.stock }}
            </div>
            <div style="flex: 1">
              <el-input-number @change="handleBuy(item)" v-model="item.num" style="width: 120px; " :min="0"
                               :max="item.stock"></el-input-number>
            </div>
          </div>
          <div style="display: flex; align-items: center; justify-content: flex-end;" v-if="item.num > 0">
            Total: <strong style="margin-right: 2px; display: inline-block; min-width: 50px; font-size: 18px;">${{
              item.total
            }}</strong>
            <el-button type="primary" @click="buy(item)">Purchase</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <div style="padding: 50px 0; text-align: center; font-size: 30px; color: #888;" v-else="">We are out of stock.
      Please wait
    </div>

    <div class="card" v-if="data.total">
      <el-pagination layout="prev, pager, next" v-model:page-size="data.pageSize" v-model:current-page="data.pageNum"
                     :total="data.total"/>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {ElMessageBox, ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  pageNum: 1,
  pageSize: 10,
  total: 0,
  goodsList: [],
  categoryList: [],
  name: '',
  activeCategoryId: null
})

const buy = (goods) => {
  let orderData = {goodsId: goods.id, num: goods.num, userId: data.user.id}
  request.post('/orders/add', orderData).then(res => {
    if (res.code === '200') {
      ElMessage.success('Order success')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleBuy = (goods) => {
  goods.total = (goods.price * goods.num).toFixed(2)
}

request.get('/category/selectAll').then(res => {
  data.categoryList = res.data
})

const loadCategoryGoods = (categoryId) => {
  console.log('Loading category goods for category:', categoryId); // Debug log
  data.activeCategoryId = categoryId
  load()
}

const load = () => {
  console.log('Loading goods with params:', {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    name: data.name,
    categoryId: data.activeCategoryId
  }); // Debug log

  request.get('/goods/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      categoryId: data.activeCategoryId
    }
  }).then(res => {
    console.log('Received goods data:', res.data); // Debug log

    data.goodsList = res.data?.list
    data.total = res.data?.total
    data.goodsList.forEach(item => {
      item.num = 0
    })
  }).catch(error => {
    console.error('Error fetching goods:', error); // Error log
  })
}

load()

</script>

<style>
.active {
  color: white !important;
  background-color: #1967e3 !important;
}
</style>
