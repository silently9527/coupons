<template>
    <view class="cu-load load-modal" v-if="show">
        <image src="/static/logo.png" mode="aspectFit"></image>
    </view>
</template>

<script>
    export default {
        name: "Loading",
        data() {
            return {
                show: true
            };
        }
    }
</script>

<style scoped>

</style>
