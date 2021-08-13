<template>
	<scroll-view style="height: 100%;"
				:scroll-y="allow_scroll_y"
				@scroll="scroll"
				@scrolltolower="scrolltolower">
		<view class="refresh-moudle"
			@touchstart="touchStart($event)"
			@touchmove="touchMove($event)"
			@touchend="touchEnd($event)"
			:style="{transform: 'translate3d(0,' + top + 'px, 0)'}">
		  <view class="pull-refresh">
			<slot name="pull-refresh">
			  <view class="down-tip" v-if="dropDownState===1">
				<image v-if="dropDownInfo.downImg" class="down-img" :src="dropDownInfo.downImg"></image>
				<view class="down-text">{{dropDownInfo.downText}}</view>
			  </view>
			  <view class="up-tip" v-if="dropDownState===2">
				<image v-if="dropDownInfo.upImg" class="up-img" :src="dropDownInfo.upImg"></image>
				<view class="up-text">{{dropDownInfo.upText}}</view>
			  </view>
			  <view class="refresh-tip" v-if="dropDownState===3">
				<image v-if="dropDownInfo.refreshImg" class="refresh-img" :src="dropDownInfo.refreshImg"></image>
				<view class="refresh-text">{{dropDownInfo.refreshText}}</view>
			  </view>
			</slot>
		  </view>
		  <slot></slot>
		  <uni-load-more :status="pullupLoadingType"></uni-load-more>
		</view>
	</scroll-view>
</template>

<script>
	import uniLoadMore from '@/components/LoadMore';
	export default {
		name: "ygcRefresh",
		components: {
			uniLoadMore
		},
		//属性
		props: {
			pullupLoadingType: {
				type: String,
				default: 'more'
			}
		},
		data () {
			return {
			  defaultOffset: 40, // 下拉偏移高度, 如果要改建议相应的修改.releshMoudle的margin-top和.down-tip, .up-tip, .refresh-tip的height
			  top: 0,
			  startY: 0,
			  isDropDown: false, // 是否下拉
			  isRefreshing: false, // 是否正在刷新
			  dropDownState: 1, // 显示1:下拉可以刷新, 2:松开立即刷新, 3:正在刷新数据中...
			  dropDownInfo: {
				downText: '下拉可以刷新',
				downImg: '/static/refresh/xiangxia.png',
				upText: '松开立即刷新',
				upImg: '/static/refresh/xiangxia.png',
				refreshText: '正在刷新数据...',
				refreshImg: '/static/refresh/loading.png'
			  },
			  scrollTop: 0,
			  allow_scroll_y: true ,//1.0.2版新增代码，解决因ios环境下scrollview的scrolltop可下拉为负数带来的体验问题
			  allowTouchMove: true //1.0.6版新增代码,控制是否允许touchMove事件
			}
		},
		//组件生命周期
		created:function(e){
		},
		methods: {
			scrolltolower() {
				this.$emit('scrolltolower');
			},
			scroll: function(e) {
				this.$nextTick(function() {
					this.scrollTop = e.detail.scrollTop;
				});
			},
			/**
			 * 触摸开始，手指点击屏幕时
			 * @param {object} e Touch 对象包含的属性
			 */
			touchStart (e) {
				// console.log('startPageY:',e.changedTouches[0].pageY);
				this.startY = e.changedTouches[0].pageY;
				this.startScrollTop = this.scrollTop;

				this.allowTouchMove = true; //1.0.6版新增代码,控制是否允许touchMove事件
			},

			/**
			 * 接触点改变，滑动时
			 * @param {object} e Touch 对象包含的属性
			 */
			touchMove (e) {
				//1.0.6版新增代码,控制是否允许touchMove事件
				if(!this.allowTouchMove) {
					return;
				}

				//1.0.2版新增代码，解决因ios环境下scrollview的scrolltop可下拉为负数带来的体验问题
				this.$nextTick(function() {
					if(this.scrollTop<0) {
						this.allow_scroll_y = false;
					} else {
						this.allow_scroll_y = true;
					}
				});

				if(this.scrollTop <= 0) {
					if (e.changedTouches[0].pageY > this.startY) {
						// 下拉
						this.isDropDown = true
						if (!this.isRefreshing) {
						  // 获取拉取的间隔差  当前移动的y点          初始的y点        初始顶部距离
						  let diff = e.changedTouches[0].pageY - this.startY -  this.startScrollTop;
						  this.top = Math.pow(diff, 0.8) + (this.dropDownState === 3 ? this.defaultOffset : 0);
						  if (this.top >= this.defaultOffset) {
							// console.log('top:',this.top);
							this.dropDownState = 2;
							e.preventDefault();
						  } else {
							this.dropDownState = 1;
							// 去掉会导致ios无法刷新
							e.preventDefault();
						  }
						}
					} else {
						this.isDropDown = false;
						this.dropDownState = 1;
					}
				}
			},

			/**
			 * 触摸结束，手指离开屏幕时
			 * @param {object} e Touch 对象包含的属性
			 */
			touchEnd (e) {
			  //1.0.2版新增代码，解决因ios环境下scrollview的scrolltop可下拉为负数带来的体验问题
			  this.$nextTick(function() {
				  this.allow_scroll_y = true;
			  });

			  if (this.isDropDown && !this.isRefreshing) {
				if (this.top >= this.defaultOffset) {
				  // do refresh
				  this.isRefreshing = true;
				  this.refresh();
				} else {
				  // cancel refresh
				  this.isRefreshing = false;
				  this.isDropDown = false;
				  this.dropDownState = 1;
				  this.top = 0;
				}
			  }
			},

			/**
			 * 刷新
			 */
			refresh () {
			  this.dropDownState = 3;
			  this.top = this.defaultOffset;
			  // 延时1200毫秒结束正在刷新动画，给用户一个刷新的感觉，或者可以直接去掉本组件正在刷新的动画状态，配合官方uni-load-more组件的正在刷新动画
			  this.$emit('onRefresh');
			  setTimeout(() => {
				this.refreshDone();
			  }, 1200)

			  /****好吧，粗暴点，就是把上面的代码注释掉，用下面的代码*****/
			  // this.$emit('onRefresh');
			  // this.refreshDone();

			},

			/**
			 * 刷新完成
			 */
			refreshDone () {
			  this.isRefreshing = false;
			  this.isDropDown = false;
			  this.dropDownState = 1;
			  this.top = 0;
			}
		}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss" scoped>
	$height: 40px;
	.refresh-moudle {
		width: 100%;
		margin-top: -$height;
		-webkit-overflow-scrolling: touch; /* ios5+ */
		.pull-refresh {
			width: 100%;
			color: #999;
			transition-duration: 200ms;
			font-size: 28upx;
			.down-tip,
			.up-tip,
			.refresh-tip {
				display: flex;
				align-items: center;
				justify-content: center;
				height: $height;
			}
			.down-img,
			.up-img,
			.refresh-img{
				width: 50upx;
				height: 50upx;
				margin-right: 30upx;
			}
			.down-img {
			  transform: rotate(0deg);
			  animation: anticlockwise 0.8s ease;
			}
			@keyframes anticlockwise {
			  0% {
				transform: rotate(-180deg);
			  }
			  100% {
				transform: rotate(0deg);
			  }
			}
			.up-img {
			  transform: rotate(180deg);
			  animation: clockwise 0.8s ease;
			}
			@keyframes clockwise {
			  0% {
				transform: rotate(0deg);
			  }
			  100% {
				transform: rotate(-180deg);
			  }
			}
			.refresh-img {
			  animation: rotating 1.5s linear infinite;
			}
			@keyframes rotating {
			  0% {
				transform: rotate(0deg);
			  }
			  100% {
				transform: rotate(1turn);
			  }
			}
		}
	}
	.uni-load-more {
		display: flex;
		flex-direction: row;
		height: 80upx;
		align-items: center;
		justify-content: center
	}
</style>
