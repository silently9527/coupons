<template>
  <div>
    <div class="plugin-center">
      <a-card class="plugin" v-for="plugin in plugins" :key="plugin.pluginId">
        <div slot="cover" class="content">
          <img width="150" :src="plugin.icon"/>
          <div class="info">
            <div class="title">
<!--              <a-tag color="cyan">官方</a-tag>-->
              {{plugin.pluginName}}
            </div>
            <div class="margin-top-3"><span class="price">价格：{{plugin.price === '0'?'免费':'￥'+plugin.price}}</span></div>
            <div class="sharer margin-top-3"><span class="label">分享者：{{plugin.author}}</span></div>
            <div class="version margin-top-3"><span class="label">版本：{{plugin.version}}</span></div>
            <div class="desc margin-top-5">{{plugin.description}}</div>
          </div>
        </div>
        <template slot="actions" class="ant-card-actions">
          <span @click="installFromPluginCenter(plugin)"><a-icon type="download"/>&nbsp;&nbsp;安装</span>
          <a :href="plugin.docUrl" target="_blank"><span><a-icon type="file-markdown"/>&nbsp;&nbsp;文档</span></a>
        </template>
      </a-card>
    </div>
    <div style="display: flex;justify-content: end">
      <pagination :current="params.currentPage" :pageSize="params.pageSize" :total="total" @change="page" show-less-items />
    </div>
    <modal v-model="visible"
           :title="'安装插件：' + installPlugin.pluginName"
           @ok="handleOk">
      <div>
        <a-form-model>
          <a-form-model-item label="插件提取码">
            <a-input placeholder="请输入提取码"/>
          </a-form-model-item>
        </a-form-model>
      </div>

      <div style="display: flex;justify-content: center;">
        <p>{{ installPlugin.remark }}</p>
      </div>
      <a-divider />
      <div style="display: flex;justify-content: center;">
        <img width="180" :src="installPlugin.qrcode"/>
      </div>
    </modal>
  </div>
</template>

<script>
import { getPluginList } from '@/api/plugin-center'
import { pagination, modal } from 'ant-design-vue'
import { fetchResult } from '@/utils/fetchUtil'

  export default {
    name: 'PluginCenter',
    components: {
      pagination, modal
    },
    data: () => {
      return {
        plugins: [
        ],
        visible: false,
        total: 0,
        params: {
          currentPage: 1,
          pageSize: 9
        },
        installPlugin: {}
      }
    },
    created () {
      this.fetchList(this.params)
    },
    methods: {
      fetchList (params) {
        getPluginList(params)
          .then(res => {
            if (fetchResult(res, false, true)) {
              this.plugins = res.data.records
              this.params.currentPage = res.data.current
              this.total = res.data.total
            }
          })
      },
      page (currentPage) {
        this.params.currentPage = currentPage
        this.fetchList(this.params)
      },
      installFromPluginCenter (plugin) {
        this.visible = true
        this.installPlugin = plugin
      },
      handleOk () {
        console.log('ok')

      }
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
        .price {
          color: #dd524d;
          font-size: 12px;
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
