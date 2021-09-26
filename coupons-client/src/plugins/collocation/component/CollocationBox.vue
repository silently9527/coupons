<template>
	<scroll-view scroll-y="true">
		<view class="list">
			<view class="item" v-for="item in list" :key="item.collocationId" @tap="navToCollocation(item)">
				<image mode="widthFix" :lazy-load="true" :src="item.mainImage"></image>
				<view class="margin-top-xs text-center">
					<text class="icon icon-hotfill text-sm margin-right-xs"></text>
					<text class="text-black text-sm">{{item.appreciateCount}}</text>
				</view>
			</view>
		</view>
	</scroll-view>
</template>

<script>
	export default {
		name: "CollocationBox",
		components: {},
		props: {
			list: {
				type: Array,
				default: () => []
			}
		},
		data() {
			return {};
		},
		methods: {
			navToCollocation(item) {
				uni.navigateTo({
					url: `/pages/collocation/detail?collocationId=${item.collocationId}`
				});
			}
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
	.list {
		display: flex;
		flex-wrap: wrap;
		padding: 20upx;
		justify-content: space-around;
	}

	.list .item {
		width: 32%;
		margin-bottom: 20upx;
	}

	.list .item .image-wrapper {
		border-radius: 3px;
		overflow: hidden;
	}

	.list .item .image-wrapper image {
		opacity: 1;
	}
</style>
