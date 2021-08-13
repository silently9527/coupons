<template>
	<view class="container">
		<title-nav bg-color="bg-white solid-bottom text-black" :isBack="true">
			<!--#ifdef APP-PLUS || H5-->
			<block slot="custom">
				<view class="cu-bar search bg-white" style="width: 100%;">
					<view class="search-form padding-left-xs">
						<text class="icon icon-search text-grey"></text>
						<input class="margin-left-xs" v-model="keyword" type="search" @input="inputChange" @confirm="doSearch(false)" :placeholder="defaultKeyword"/>
					</view>
				</view>
				<view class="action" style="width:100px;">
					<button class="cu-btn shadow-blur" @tap="doSearch(false)">搜索</button>
				</view>
			</block>
			<!-- #endif -->
			<!--#ifdef MP-WEIXIN || MP-QQ-->
			<block slot="content">搜索</block>
			<!-- #endif -->
		</title-nav>

		<!--#ifdef MP-WEIXIN || MP-QQ-->
		<view style="display: flex;">
			<view class="cu-bar search bg-white" style="width: 100%;padding-top:5px;">
				<view class="search-form padding-left-xs">
					<text class="icon icon-search text-grey"></text>
					<input class="margin-left-xs" v-model="keyword" type="search" @input="inputChange" @confirm="doSearch(false)" :placeholder="defaultKeyword"/>
				</view>
			</view>
			<view class="action" style="width:100px;background-color: white;padding-top:5px;">
				<button class="cu-btn shadow-blur" @tap="doSearch(false)">搜索</button>
			</view>
		</view>
		<!-- #endif -->

		<view class="search-keyword">
			<scroll-view class="keyword-list-box" v-show="isShowKeywordList" scroll-y>
				<view class="keyword-entry" hover-class="keyword-entry-tap" v-for="row in keywordList" :key="row.keyword">
					<view class="keyword-text" @tap="doSearch(row.keyword)"><rich-text :nodes="row.htmlStr"></rich-text></view>
				</view>
			</scroll-view>
			<scroll-view class="keyword-box" v-show="!isShowKeywordList" scroll-y>
				<view class="keyword-block" v-if="oldKeywordList.length > 0">
					<view class="keyword-list-header solid-bottom">
						<view>历史搜索</view>
						<view @tap="oldDelete"><text class="icon icon-delete text-grey"></text></view>
					</view>
					<view class="keyword">
						<view v-for="(keyword, index) in oldKeywordList" @tap="doSearch(keyword)" :key="index">{{ keyword }}</view>
					</view>
				</view>
				<view class="keyword-block">
					<view class="keyword-list-header solid-bottom">
						<view>热门搜索</view>
						<view @tap="hotToggle"><text class="icon text-grey" :class="!forbid?'icon-attention':'icon-attentionforbid'"></text></view>
					</view>
					<view class="keyword" v-if="!forbid">
						<view v-for="(keyword, index) in hotKeywordList" @tap="doSearch(keyword)" :key="index">{{ keyword }}</view>
					</view>
					<view class="hide-hot-tis padding-top-xs" v-else><view>当前搜热门搜索已隐藏</view></view>
				</view>
			</scroll-view>
		</view>


	</view>
</template>

<script>
  export default {
    components: {},
    data() {
      return {
        defaultKeyword: '',
        keyword: '',
        oldKeywordList: [],
        hotKeywordList: [],
        keywordList: [],
        forbid: false,
        isShowKeywordList: false
      };
    },
    onLoad() {
      this.init();
    },
    methods: {
      init() {
        this.loadDefaultKeyword();
        this.loadOldKeyword();
        this.loadHotKeyword();
      },
      //加载默认搜索关键字
      loadDefaultKeyword() {
        uni.getStorage({
          key: 'oldkeys',
          success: res => {
            var oldkeys = JSON.parse(res.data);
            this.defaultKeyword = oldkeys[0];
          },
          fail: res => {
            this.defaultKeyword = "请搜索时尚商品"
          }
        });
      },
      //加载历史搜索,自动读取本地Storage
      loadOldKeyword() {
        uni.getStorage({
          key: 'oldkeys',
          success: res => {
            this.oldKeywordList = JSON.parse(res.data);
          }
        });
      },
      //加载热门搜索
      async loadHotKeyword() {
        this.hotKeywordList = this.$cache.get('hot_keywords', []);
        if (this.hotKeywordList.length === 0) {
          let response = await this.$api.backend.getHotSearch();
          this.$cache.put('hot_keywords', response.data, 60 * 60 * 24)
          this.hotKeywordList = response.data
        }
        if (!this.defaultKeyword) {
          this.defaultKeyword = this.hotKeywordList[0]
        }
      },
      //监听输入
      inputChange(event) {
        //兼容引入组件时传入参数情况
        var keyword = event.detail ? event.detail.value : event;
        if (!keyword) {
          this.keywordList = [];
          this.isShowKeywordList = false;
          return;
        }
        this.isShowKeywordList = true;
        //以下示例截取淘宝的关键字，请替换成你的接口
        uni.request({
          url: 'https://suggest.taobao.com/sug?code=utf-8&q=' + keyword, //仅为示例
          success: res => {
            this.keywordList = this.drawCorrelativeKeyword(res.data.result, keyword);
          }
        });
      },
      //高亮关键字
      drawCorrelativeKeyword(keywords, keyword) {
        var len = keywords.length,
          keywordArr = [];
        for (var i = 0; i < len; i++) {
          var row = keywords[i];
          //定义高亮#9f9f9f
          var html = row[0].replace(keyword, "<span style='color: #9f9f9f;'>" + keyword + '</span>');
          html = '<div>' + html + '</div>';
          var tmpObj = {
            keyword: row[0],
            htmlStr: html
          };
          keywordArr.push(tmpObj);
        }
        return keywordArr;
      },
      //清除历史搜索
      oldDelete() {
        uni.showModal({
          content: '确定清除历史搜索记录？',
          success: res => {
            if (res.confirm) {
              this.oldKeywordList = [];
              uni.removeStorage({
                key: 'oldkeys'
              });
            } else if (res.cancel) {
            }
          }
        });
      },
      //热门搜索开关
      hotToggle() {
        this.forbid = !this.forbid;
      },
      //执行搜索
      async doSearch(key) {
        key = key ? key : this.keyword ? this.keyword : this.defaultKeyword;
        this.keyword = key;
        this.saveKeyword(key); //保存为历史
        uni.navigateTo({
          url: `/pages/search/result?keyword=${key}`
        });
      },
      //保存关键字到历史记录
      saveKeyword(keyword) {
        uni.getStorage({
          key: 'oldkeys',
          success: res => {
            var oldkeys = JSON.parse(res.data);
            var findIndex = oldkeys.indexOf(keyword);
            if (findIndex === -1) {
              oldkeys.unshift(keyword);
            } else {
              oldkeys.splice(findIndex, 1);
              oldkeys.unshift(keyword);
            }
            //最多10个纪录
            oldkeys.length > 10 && oldkeys.pop();
            uni.setStorage({
              key: 'oldkeys',
              data: JSON.stringify(oldkeys)
            });
            this.oldKeywordList = oldkeys; //更新历史搜索
          },
          fail: e => {
            var oldkeys = [keyword];
            uni.setStorage({
              key: 'oldkeys',
              data: JSON.stringify(oldkeys)
            });
            this.oldKeywordList = oldkeys; //更新历史搜索
          }
        });
      }
    }
  };
</script>
<style scoped>
view {
	display: block;
}
.search-keyword {
	width: 100%;
	background-color: rgb(242, 242, 242);
}
.keyword-list-box {
	height: calc(100vh - 110upx);
	padding-top: 10upx;
	background-color: #fff;
}
.keyword-entry {
	width: 94%;
	height: 80upx;
	margin: 0 3%;
	font-size: 30upx;
	color: #333;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: solid 1upx #e7e7e7;
}
.keyword-entry image {
	width: 60upx;
	height: 60upx;
}
.keyword-entry .keyword-text,
.keyword-entry .keyword-img {
	height: 80upx;
	display: flex;
	align-items: center;
}
.keyword-entry .keyword-text {
	width: 90%;
}
.keyword-entry .keyword-img {
	width: 10%;
	justify-content: center;
}
.keyword-box {
	height: calc(100vh - 110upx);
	background-color: #fff;
}
.keyword-box .keyword-block {
	padding: 10upx 0;
}
.keyword-box .keyword-block .keyword-list-header {
	width: 100%;
	padding: 10upx 3%;
	font-size: 27upx;
	color: #333;
	display: flex;
	justify-content: space-between;
}
.keyword-box .keyword-block .keyword-list-header image {
	width: 40upx;
	height: 40upx;
}
.keyword-box .keyword-block .keyword {
	width: 100%;
	padding: 3px 3%;
	display: flex;
	flex-flow: wrap;
	justify-content: flex-start;
}
.keyword-box .keyword-block .hide-hot-tis {
	display: flex;
	justify-content: center;
	font-size: 28upx;
	color: #6b6b6b;
}
.keyword-box .keyword-block .keyword > view {
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 10upx;
	padding: 0 30upx;
	margin: 10upx 20upx 10upx 0;
	height: 60upx;
	font-size: 28upx;
	background-color: rgb(242, 242, 242);
	color: #6b6b6b;
}
</style>
