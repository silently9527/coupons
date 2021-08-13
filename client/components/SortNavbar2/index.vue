<template>
  <view class="navbar" :style="{position:headerPosition,top:headerTop}">
    <view class="nav-item" :class="{current: filterSort === 'tk_total_sales_des'}" @click="sortClick('tk_total_sales_des')">
      人气
    </view>
    <view class="nav-item" :class="{current: filterSort === 'total_sales_des'}" @click="sortClick('total_sales_des')">
      销量
    </view>
    <view class="nav-item" :class="{current: filterSort.indexOf('price')>-1}" @click="sortClick('price')">
      <text>价格</text>
      <view class="p-box">
        <text :class="{active: priceSort === 'asc' && filterSort.indexOf('price')>-1}" class="yticon icon icon-triangleupfill"></text>
        <text :class="{active: priceSort === 'des' && filterSort.indexOf('price')>-1}"
              class="yticon margin-top-ss icon icon-triangledownfill"></text>
      </view>
    </view>
    <view class="nav-item" @click="clickCoupon">
      <text class="icon" :class="hasCoupon?'icon-squarecheckfill text-red':'icon-square text-grey'"></text>
      <text>优惠券</text>
    </view>
  </view>
</template>

<script>
  export default {
    name: "SortNavbar",
    props: {
      headerTop: {
        type: String,
        default: () => {
          return '0px'
        }
      }
    },
    data() {
      return {
        headerPosition: "fixed",
        filterSort: 'tk_total_sales_des',
        hasCoupon: false,
        priceSort: 'asc'
      }
    },
    methods: {
      clickCoupon() {
        this.hasCoupon = !this.hasCoupon
        this.sortClick(this.filterSort, 2)
      },
      sortClick(sort, type=1) {
        if (this.filterSort === sort && sort !== 'price' && type===1) {
          return;
        }
        if (sort === 'price') {
          this.priceSort = this.priceSort === 'des' ? 'asc' : 'des';
          this.filterSort = sort + '_' + this.priceSort;
          this.$emit('sort', {
            sort: this.filterSort,
            hasCoupon: this.hasCoupon
          });
        } else {
          this.filterSort = sort;
          this.$emit('sort', {sort: this.filterSort, hasCoupon: this.hasCoupon});
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
