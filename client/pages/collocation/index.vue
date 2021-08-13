<template>
  <view class="container">
    <view class="bg-white fixed nav text-center" style="font-size: 32rpx;" :style="[{height:CustomBar + 'px'}]">
      <view class="cu-item" :class="isRecommend?'cur text-red':''" @tap="showRecommend()"
            :style="[{height:CustomBar + 'px',lineHeight:(CustomBar+20)+'px'}]">
        推荐
      </view>
      <view class="cu-item" :class="isShopping?'cur text-red':''" @tap="showShopping()"
            :style="[{height:CustomBar + 'px',lineHeight:(CustomBar+20)+'px'}]">
        逛街
      </view>
    </view>
    <view v-if="isRecommend" :style="[{marginTop: CustomBar+'px'}]">
      <refresh @onRefresh="onRecommendRefresh" :pullupLoadingType="loadingType">
        <collocation-card v-for="item in collocations" :key="item.collocationId" :item="item" @appreciate="appreciate" @detail="navCollocationDetail"/>
      </refresh>
    </view>
    <view v-if="isShopping" :style="[{marginTop: CustomBar+'px'}]">
      <refresh @onRefresh="onCollocationListRefresh" :pullupLoadingType="loadingType2">
        <collocation-box :list="collocations2"></collocation-box>
      </refresh>
      <fab-btn class="fab-btn" :content="content" :horizontal="'right'" :vertical="'bottom'"
               :direction="'vertical'" @trigger="clickSex" @fabClick="clearSex">
      </fab-btn>
    </view>
  </view>
</template>

<script>
  import CollocationCard from "./component/CollocationCard"
  import CollocationBox from "./component/CollocationBox"
  import Refresh from '@/components/Refresh'
  import FabBtn from '@/components/FabBtn';
  import { mapGetters } from 'vuex';

  export default {
    components: {
      CollocationCard, Refresh, CollocationBox, FabBtn
    },
    data() {
      return {
        CustomBar: this.CustomBar,
        isRecommend: true,
        isShopping: false,
        loadingType: 'more',
        loadingType2: 'more',
        collocations: [],
        collocations2: [],
        sex: undefined,
        page: 0,
        page2: 0,
        content: [{
          iconPath: '/static/male.png',
          text: '男生',
          value: '1'
        }, {
          iconPath: '/static/female.png',
          text: '女生',
          value: '0'
        }]
      };
    },
    computed: {
      ...mapGetters(['hasLogin'])
    },
    onLoad(options) {
      this.loadRecommendData();
      this.loadCollocationList();
    },
    onReachBottom() {
      if (this.isRecommend) {
        this.loadRecommendData();
      } else if (this.isShopping) {
        this.loadCollocationList();
      }
    },
    onShareAppMessage(res) {
      return {
        title: '大神教会你搭配，购物先领取优惠券，爱穿搭更爱省钱',
        path: '/pages/index/index',
        imageUrl: 'http://file.xiaoyizhiku.net/170/share01.png'
      };
    },
    methods: {
      clickSex(menu) {
        this.sex = menu.item.value;
        this.onCollocationListRefresh();
        uni.pageScrollTo({
          duration: 300,
          scrollTop: 0
        })
      },
      clearSex() {
        if(this.sex){
          this.sex = undefined;
          this.onCollocationListRefresh();
          uni.pageScrollTo({
            duration: 300,
            scrollTop: 0
          })
        }
      },
      showRecommend() {
        this.isRecommend = true;
        this.isShopping = false;
      },
      showShopping() {
        this.isRecommend = false;
        this.isShopping = true;
      },
      onRecommendRefresh(){
        this.loadingType = 'more'
        this.page = 0
        this.loadRecommendData('refresh')
      },
      onCollocationListRefresh(){
        this.loadingType2 = 'more'
        this.page2 = 0
        this.loadCollocationList('refresh')
      },
      async loadRecommendData(type) {
        let result = await this.$api.backend.recommendCollocations(this.page++);
        let list = result.data.content || [];
        if (type === 'refresh') {
          this.collocations = list;
        } else {
          this.collocations = this.collocations.concat(list);
        }
        this.loadingType = list.length === 0 || list.length < 20 ? 'nomore' : 'more';
      },
      async loadCollocationList(type) {
        let result = await this.$api.backend.collocationsList(this.page2++, this.sex);
        let list = result.data.content || [];
        if (type === 'refresh') {
          this.collocations2 = list;
        } else {
          this.collocations2 = this.collocations2.concat(list);
        }
        this.loadingType2 = list.length === 0 || list.length < 24 ? 'nomore' : 'more';
      },
      navCollocationDetail(item) {
        uni.navigateTo({ url: `/pages/collocation/detail?collocationId=${item.collocationId}` });
      },
      appreciate(item) {
        if(!this.hasLogin){
          uni.showToast({
            title: '您还未登录，请先登录',
            icon: 'none',
            duration: 2000
          });
          return;
        }
        let item2 = this.collocations.find(data=>{
          return data.collocationId === item.collocationId
        });

        if(item2.appreciate){
          this.$api.backend.cancelCollocationAppreciate(item2.collocationId).then(()=>{
            item2.appreciate = false;
            item2.appreciateCount -= 1;
          });
        }else{
          this.$api.backend.addCollocationAppreciate(item2.collocationId).then(()=>{
            item2.appreciate = true;
            item2.appreciateCount += 1;
          });
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  page, .container {
    background: $page-color-base;
  }
</style>
