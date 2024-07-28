<template>
  <div>

    <div class="card" style="line-height: 30px">
      <div>欢迎您，{{ data.user.name }} 祝您今天过得开心！</div>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
})
</script>