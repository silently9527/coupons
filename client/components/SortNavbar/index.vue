<template>
	<view class="navbar" :style="{position:headerPosition,top:headerTop}">
		<view class="nav-item" :class="{current: filterSort === 0}" @click="sortClick(0)">
			人气
		</view>
		<view class="nav-item" :class="{current: filterSort === 2}" @click="sortClick(2)">
			销量
		</view>
		<view class="nav-item" :class="{current: filterSort === 1}" @click="sortClick(1)">
			最新
		</view>
		<view class="nav-item" :class="{current: filterSort === 10}" @click="sortClick(10)">
			<text>价格</text>
			<view class="p-box">
				<text :class="{active: priceSort === 6 && filterSort === 10}" class="yticon icon icon-triangleupfill"></text>
				<text :class="{active: priceSort === 5 && filterSort === 10}" class="yticon margin-top-ss icon icon-triangledownfill"></text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: "SortNavbar",
		props: {
            headerTop: {
                type: String,
				default: ()=> {
				    return '0px'
				}
			}
		},
		data() {
			return {
                filterSort: 0,
                priceSort: 0,
                headerPosition:"fixed"
			}
		},
        methods: {
            sortClick(sort){
                if(this.filterSort === sort && sort !== 10){
                    return;
                }
                this.filterSort = sort;
                if(sort === 10){
                    this.priceSort = this.priceSort === 5 ? 6: 5;
                    this.$emit('sort',  this.priceSort);
                }else{
                    this.priceSort = 0;
                    this.$emit('sort', sort);
                }
            }
        }
	}
</script>

<style lang="scss" scoped>
	.navbar{
		position: fixed;
		left: 0;
		top: var(--window-top);
		display: flex;
		width: 100%;
		height: 80upx;
		background: #fff;
		box-shadow: 0 2upx 10upx rgba(0,0,0,.06);
		z-index: 10;
		.nav-item{
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			font-size: 30upx;
			color: $font-color-dark;
			position: relative;
			&.current{
				color: #e54d42;
				&:after{
					content: '';
					position: absolute;
					left: 50%;
					bottom: 0;
					transform: translateX(-50%);
					width: 120upx;
					height: 0;
					border-bottom: 4upx solid $base-color;
				}
			}
		}
		.p-box{
			display: flex;
			flex-direction: column;
			.yticon{
				display: flex;
				align-items: center;
				justify-content: center;
				width: 30upx;
				height: 12upx;
				line-height: 1;
				margin-left: 4upx;
				font-size: 11px;
				color: #d8d8d8;
				&.active{
					color: $base-color;
				}
			}
			.xia{
				transform: scaleY(-1);
			}
		}
		.cate-item{
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			width: 80upx;
			position: relative;
			font-size: 44upx;
			&:after{
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				border-left: 1px solid #ddd;
				width: 0;
				height: 36upx;
			}
		}
	}

	.margin-top-ss {
		margin-top: 5upx;
	}
</style>
