<template>
    <view class="container">
        <title-nav bg-color="bg-white solid-bottom" :isBack="true">
            <block slot="content">新手引导</block>
        </title-nav>
        <rich-text :nodes="images"></rich-text>
    </view>
</template>

<script>
    export default {
        components: {},
        data() {
            return {
                images: [{
                    name: "div",
                    attrs: {
                        style: "width:100%"
                    },
                    children: [{
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_01.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_02.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_03.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_04.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_05.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_06.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }, {
                        name: "img",
                        attrs: {
                            src: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_07.jpg?v=202003091818',
                            style: "width:100%;display:block;"
                        }
                    }]
                }]
            };
        },
        onLoad() {
        },
        onShareAppMessage(res) {
            return {
                title: '新手教程',
                path: '/pages/user/guide',
                imageUrl: 'https://cmsstatic.ffquan.cn//wap_new/activity/voucher_help/ant_01.jpg?v=202003091818'
            }
        },
        methods: {}
    }
</script>

<style lang='scss' scoped>

</style>
