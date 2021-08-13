<template>
  <view class="container">
    <title-nav bg-color="bg-white solid-bottom">
      <block slot="content">商品分类</block>
    </title-nav>
    <view class="VerticalBox">
      <scroll-view class="VerticalNav nav" scroll-y scroll-with-animation :scroll-top="verticalNavTop" style="height:calc(100vh)">
        <view class="cu-item"
              :class="index===tabCur?'cur':''" v-for="(item,index) in list"
              :key="index"
              @tap="TabSelect"
              :data-id="index">{{item.cname}}
        </view>
      </scroll-view>
      <scroll-view class="VerticalMain" scroll-y scroll-with-animation style="height:calc(100vh)" :scroll-into-view="'main-'+mainCur"
                   @scroll="VerticalMain">
        <view class="padding-tlr5" v-for="(item,index) in list" :key="index" :id="'main-'+index">
          <view class="cu-bar solid-bottom bg-white justify-center">
            <view class="action">
              <text class="text">{{item.cname}}</text>
            </view>
          </view>

          <view class="cu-list grid col-3 no-border">
            <view class="cu-item align-center" :key="subItem.subcid" v-for="subItem in item.subcategories"
                  @tap='navToList(item.cid, subItem.subcid)'>
              <view class="cu-avatar round" :style="'background-image:url('+subItem.scpic+');'"></view>
              <text>{{subItem.subcname}}</text>
            </view>
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        list: [],
        tabCur: 0,
        mainCur: 0,
        verticalNavTop: 0,
        load: true
      };
    },
    onLoad() {
      this.loadData();
    },
    onReady() {
    },
    methods: {
      async loadData() {
        this.list = this.$cache.get('category_cached', []);
        if (this.list.length === 0) {
          let result = await this.$api.backend.getCate();
          this.list = result.data;
          this.$cache.put('category_cached', this.list, 60 * 60 * 7)
        }
      },
      navToList(cid, subcid) {
        uni.navigateTo({
          url: `/pages/product/list?subcid=${subcid}`
        })
      },
      TabSelect(e) {
        this.tabCur = e.currentTarget.dataset.id;
        this.mainCur = e.currentTarget.dataset.id;
        this.verticalNavTop = (e.currentTarget.dataset.id - 1) * 50
      },
      VerticalMain(e) {
        let that = this;
        let tabHeight = 0;
        if (this.load) {
          for (let i = 0; i < this.list.length; i++) {
            let view = uni.createSelectorQuery().select("#main-" + i);
            view.fields({
              size: true
            }, data => {
              this.list[i].top = tabHeight;
              tabHeight = tabHeight + data.height;
              this.list[i].bottom = tabHeight;
            }).exec();
          }
          this.load = false
        }
        let scrollTop = e.detail.scrollTop + 10;
        for (let i = 0; i < this.list.length; i++) {
          if (scrollTop > this.list[i].top && scrollTop < this.list[i].bottom) {
            this.verticalNavTop = (i - 1) * 50
            this.tabCur = i
            return false
          }
        }
      }
    },
  }
</script>

<style lang='scss' scoped>
  page, .container {
    height: 100%;
    background-color: $page-color-base;
  }

  .fixed {
    position: fixed;
    z-index: 99;
  }

  .VerticalNav.nav {
    width: 200upx;
    white-space: initial;
  }

  .VerticalNav.nav .cu-item {
    width: 100%;
    text-align: center;
    background-color: #fff;
    color: grey;
    margin: 0;
    border: none;
    height: 50px;
    line-height: 50px;
    position: relative;
  }

  .VerticalNav.nav .cu-item.cur {
    background-color: $page-color-base;
    color: #e54d42;
  }

  .VerticalNav.nav .cu-item.cur::after {
    content: "";
    width: 6upx;
    height: 90upx;
    /* border-radius: 10upx 0 0 10upx; */
    position: absolute;
    background-color: currentColor;
    top: 0;
    left: 0;
    bottom: 0;
    margin: auto;
  }

  .VerticalBox {
    display: flex;
  }

  .VerticalMain {
    background-color: $page-color-base;
    flex: 1;
  }

  .padding-tlr5 {
    padding-top: 5px;
    padding-left: 5px;
    padding-right: 5px;
  }
</style>
