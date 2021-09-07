<template>
  <div class="page-header-index-wide page-header-wrapper-grid-content-main">
    <a-row :gutter="24">
      <a-col :md="24" :lg="7">
        <a-card :bordered="false">
          <div class="account-center-avatarHolder">
            <div class="avatar">
              <img :src="getAvatarImgUrl()">
            </div>
            <div class="username">{{ userInfo.username }}</div>
            <div class="bio">{{ userInfo.name }}</div>
          </div>
          <div class="account-center-detail">
            <p>
              <a-icon type="phone" />{{ userInfo.phone }}
            </p>
            <p>
              <a-icon type="mail" />{{ userInfo.email }}
            </p>
            <p>
              <a-icon type="environment" /> {{ userInfo.lastLoginIp }}
            </p>
            <p>
              <a-icon type="dashboard" /> {{ userInfo.lastGmtLoginTime }}
            </p>
          </div>
          <a-divider/>
          <a-divider :dashed="true"/>

          <div class="account-center-team">
            <div class="teamTitle">所属角色</div>
            <div class="members">
              <a-row>
                <a-col :span="12" v-for="(role, index) in userInfo.roles" :key="index">
                  <a-tag color="#2db7f5">
                    {{ role }}
                  </a-tag>
                </a-col>
              </a-row>
            </div>
          </div>
        </a-card>
      </a-col>
      <a-col :md="24" :lg="17">
        <a-card
          style="width:100%"
          :bordered="false"
          :tabList="tabListNoTitle"
          :activeTabKey="noTitleKey"
          @tabChange="key => handleTabChange(key, 'noTitleKey')"
        >
          <login-log v-if="noTitleKey === 'loginLog'"></login-log>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { PageView, RouteView } from '@/layouts'
import { LoginLog } from './page'
import { getAvatarUrl } from '@/api/user-avatar'
import { mapGetters } from 'vuex'

export default {
  components: {
    RouteView,
    PageView,
    LoginLog
  },
  data () {
    return {
      tags: ['很有想法的', '专注设计', '辣~', '大长腿', '川妹子', '海纳百川'],

      tagInputVisible: false,
      tagInputValue: '',

      tabListNoTitle: [
        {
          key: 'loginLog',
          tab: '登录日志'
        }
      ],
      noTitleKey: 'loginLog'
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created () {
  },
  methods: {
    getAvatarImgUrl () {
      return getAvatarUrl(this.userInfo.avatar)
    },
    handleTabChange (key, type) {
      this[type] = key
    }
  }
}
</script>

<style lang="less" scoped>
.page-header-wrapper-grid-content-main {
  width: 100%;
  height: 100%;
  min-height: 100%;
  transition: 0.3s;

  .account-center-avatarHolder {
    text-align: center;
    margin-bottom: 24px;

    & > .avatar {
      margin: 0 auto;
      width: 104px;
      height: 104px;
      margin-bottom: 20px;
      border-radius: 50%;
      overflow: hidden;
      img {
        height: 100%;
        width: 100%;
      }
    }

    .username {
      color: rgba(0, 0, 0, 0.85);
      font-size: 20px;
      line-height: 28px;
      font-weight: 500;
      margin-bottom: 4px;
    }
  }

  .account-center-detail {
    p {
      margin-bottom: 8px;
      padding-left: 26px;
      position: relative;
    }

    i {
      position: absolute;
      height: 14px;
      width: 14px;
      left: 0;
      top: 4px;
    }

    .title {
      background-position: 0 0;
    }
    .group {
      background-position: 0 -22px;
    }
    .address {
      background-position: 0 -44px;
    }
  }

  .account-center-tags {
    .ant-tag {
      margin-bottom: 8px;
    }
  }

  .account-center-team {
    .members {
      a {
        display: block;
        margin: 12px 0;
        line-height: 24px;
        height: 24px;
        .member {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.65);
          line-height: 24px;
          max-width: 100px;
          vertical-align: top;
          margin-left: 12px;
          transition: all 0.3s;
          display: inline-block;
        }
        &:hover {
          span {
            color: #1890ff;
          }
        }
      }
    }
  }

  .tagsTitle,
  .teamTitle {
    font-weight: 500;
    color: rgba(0, 0, 0, 0.85);
    margin-bottom: 12px;
  }
}
</style>
