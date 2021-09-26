<template>
	<view v-if="show" class="mask" @click="toggleMask" @touchmove.stop.prevent="stopPrevent"
		:style="{backgroundColor: backgroundColor}"
	>
		<view
			class="mask-content"
			@click.stop.prevent="stopPrevent"
			:style="[{
				height: config.height,
				transform: transform
			}]"
		>
			<scroll-view class="view-content" scroll-y>
				<view class="share-header">
					分享到
				</view>
				<view class="share-list">
					<view
						v-for="(item, index) in shareList" :key="index"
						class="share-item"
						@click="shareToFriend(item.text)"
					>
						<image :src="item.icon" mode=""></image>
						<text>{{item.text}}</text>
					</view>
				</view>
			</scroll-view>
			<view class="bottom b-t" @click="toggleMask">取消</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				transform: 'translateY(50vh)',
				timer: 0,
				backgroundColor: 'rgba(0,0,0,0)',
				show: false,
				config: {},
			};
		},
		props:{
			contentHeight:{
				type: Number,
				default: 0
			},
			//是否是tabbar页面
			hasTabbar:{
				type: Boolean,
				default: false
			},
			shareList:{
				type: Array,
				default: function(){
					return [];
				}
			}
		},
		created() {
			const height = uni.upx2px(this.contentHeight) + 'px';
			this.config = {
				height: height,
				transform: `translateY(${height})`,
				backgroundColor: 'rgba(0,0,0,.4)',
			}
			this.transform = this.config.transform;
		},
		methods:{
			toggleMask(){
				//防止高频点击
				if(this.timer == 1){
					return;
				}
				this.timer = 1;
				setTimeout(()=>{
					this.timer = 0;
				}, 500)

				if(this.show){
					this.transform = this.config.transform;
					this.backgroundColor = 'rgba(0,0,0,0)';
					setTimeout(()=>{
						this.show = false;
						this.hasTabbar && uni.showTabBar();
					}, 200)
					return;
				}

				this.show = true;
				//等待mask重绘完成执行
				if(this.hasTabbar){
					uni.hideTabBar({
						success: () => {
							setTimeout(()=>{
								this.backgroundColor = this.config.backgroundColor;
								this.transform = 'translateY(0px)';
							}, 10)
						}
					});
				}else{
					setTimeout(()=>{
						this.backgroundColor = this.config.backgroundColor;
						this.transform = 'translateY(0px)';
					}, 10)
				}
			},
			//防止冒泡和滚动穿透
			stopPrevent(){},
			//分享操作
			shareToFriend(type){
				this.$api.msg(`分享给${type}`);
				this.toggleMask();
			},
		}
	}
</script>

<style scoped>
	@charset "UTF-8";
	/* 页面左右间距 */
	/* 文字尺寸 */
	/*文字颜色*/
	/* 边框颜色 */
	/* 图片加载中颜色 */
	/* 行为相关颜色 */
	.mask {
	  position: fixed;
	  left: 0;
	  top: 0;
	  right: 0;
	  bottom: 0;
	  display: flex;
	  justify-content: center;
	  align-items: flex-end;
	  z-index: 998;
	  transition: .3s;
	}
	
	.mask .bottom {
	  position: absolute;
	  left: 0;
	  bottom: 0;
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  width: 100%;
	  height: 90upx;
	  background: #fff;
	  z-index: 9;
	  font-size: 30upx;
	  color: #303133;
	}
	
	.mask-content {
	  width: 100%;
	  height: 580upx;
	  transition: .3s;
	  background: #fff;
	}
	
	.mask-content.has-bottom {
	  padding-bottom: 90upx;
	}
	
	.mask-content .view-content {
	  height: 100%;
	}
	
	.share-header {
	  height: 110upx;
	  font-size: 30upx;
	  color: font-color-dark;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  padding-top: 10upx;
	}
	
	.share-header:before, .share-header:after {
	  content: '';
	  width: 240upx;
	  heighg: 0;
	  border-top: 1px solid #E4E7ED;
	  transform: scaleY(0.5);
	  margin-right: 30upx;
	}
	
	.share-header:after {
	  margin-left: 30upx;
	  margin-right: 0;
	}
	
	.share-list {
	  display: flex;
	  flex-wrap: wrap;
	}
	
	.share-item {
	  min-width: 33.33%;
	  display: flex;
	  flex-direction: column;
	  justify-content: center;
	  align-items: center;
	  height: 180upx;
	}
	
	.share-item image {
	  width: 80upx;
	  height: 80upx;
	  margin-bottom: 16upx;
	}
	
	.share-item text {
	  font-size: 28upx;
	  color: #606266;
	}

</style>
