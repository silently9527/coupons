<template>
	<view class="content">

		<view class="mix-list-cell" :class="border" @click="eventClick" hover-class="cell-hover"  :hover-stay-time="50">
			<text
				v-if="icon"
				class="icon"
				:style="[{
					color: iconColor,
				}]"
				:class="icon"
			></text>
			<text class="margin-left-xs cell-tit clamp">{{title}}</text>
			<text v-if="tips" class="cell-tip">{{tips}}</text>
			<text class="icon icon-right text-gray"
				:class="typeList[navigateType]"
			></text>
		</view>

	</view>
</template>

<script>
	/**
	 *  简单封装了下， 应用范围比较狭窄，可以在此基础上进行扩展使用
	 *  比如加入image， iconSize可控等
	 */
	export default {
		data() {
			return {
				typeList: {
					left: 'icon-zuo',
					right: 'icon-you',
					up: 'icon-shang',
					down: 'icon-xia'
				},
			}
		},
		props: {
			icon: {
				type: String,
				default: ''
			},
			title: {
				type: String,
				default: '标题'
			},
			tips: {
				type: String,
				default: ''
			},
			navigateType: {
				type: String,
				default: 'right'
			},
			border: {
				type: String,
				default: 'b-b'
			},
			hoverClass: {
				type: String,
				default: 'cell-hover'
			},
			iconColor: {
				type: String,
				default: '#333'
			}
		},
		methods: {
			eventClick(){
				this.$emit('eventClick');
			}
		},
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
	.icon .mix-list-cell.b-b:after {
	  left: 90upx;
	}
	
	.mix-list-cell {
	  display: flex;
	  align-items: center;
	  padding: 20upx 10upx;
	  line-height: 60upx;
	  position: relative;
	}
	
	.mix-list-cell.cell-hover {
	  background: #fafafa;
	}
	
	.mix-list-cell.b-b:after {
	  left: 30upx;
	}
	
	.mix-list-cell .cell-icon {
	  align-self: center;
	  width: 56upx;
	  max-height: 60upx;
	  font-size: 38upx;
	}
	
	.mix-list-cell .cell-more {
	  align-self: center;
	  font-size: 30upx;
	  color: #606266;
	  margin-left: 10px;
	}
	
	.mix-list-cell .cell-tit {
	  flex: 1;
	  font-size: 28upx;
	  color: #303133;
	  margin-right: 10upx;
	}
	
	.mix-list-cell .cell-tip {
	  font-size: 26upx;
	  color: #909399;
	}

</style>
