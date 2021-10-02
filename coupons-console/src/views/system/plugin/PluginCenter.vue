<template>
  <div>
    <div class="plugin-center">
      <a-card class="plugin" v-for="plugin in plugins" :key="plugin.pluginId">
        <div slot="cover" class="content">
          <img src="https://tva1.sinaimg.cn/large/008i3skNgy1guz0mpc7f5j605u05sq2u02.jpg"/>
          <div class="info">
            <div class="title">
              <a-tag color="cyan">官方</a-tag>MySQL-Percona分支单机安装镜像
            </div>
            <div class="sharer margin-top-3"><span class="label">分享者：Silently9527</span></div>
            <div class="version margin-top-3"><span class="label">版本：1.0.4</span></div>
            <div class="desc margin-top-5">页面跳转路径有层级限制，转路径不能无限制跳转新页不能无限制跳转新页面 s.json里注册的vue页面。</div>
          </div>
        </div>
        <template slot="actions" class="ant-card-actions">
          <span><a-icon type="download"/>&nbsp;&nbsp;安装</span>
          <span><a-icon type="file-markdown"/>&nbsp;&nbsp;文档</span>
        </template>
      </a-card>
    </div>
    <div style="display: flex;justify-content: end">
      <pagination v-model="params.page" :total="50" show-less-items/>
    </div>
  </div>
</template>

<script>
import { pagination } from 'ant-design-vue'

  export default {
    name: 'PluginCenter',
    components: {
      pagination
    },
    data: () => {
      return {
        plugins: [
          {
            pluginId: 123
          },
          {
            pluginId: 232
          },
          {
            pluginId: 2111132
          },
          {
            pluginId: 21245
          },
          {
            pluginId: 432
          },
          {
            pluginId: 432
          }
        ],
        params: {
          page: 1,
          size: 10
        }
      }
    },
    mounted () {

    },
    methods: {
    }
  }
</script>

<style lang="less" scoped>
.plugin-center {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  .plugin {
    margin-right: 28px;
    margin-bottom: 28px;
    width: 32%;
    .content {
      padding: 15px;
      display: flex;
      flex-direction: row;
      img {
        width: 120px;
      }
      .info {
        margin-left: 10px;
        .title {
          font-size: 15px;
          font-weight: bold;
          color: #4091f7;
        }
        .sharer {
          font-size: 12px;
        }
        .desc {
          font-size: 13px;
        }
      }
    }
  }
}

.plugin:nth-child(3n) {
  margin-right: 0;
}

.label {
  color: #cccccc;
  font-size: 12px;
}

.margin-top-3 {
  margin-top: 3px;
}
.margin-top-5 {
  margin-top: 5px;
}
</style>
