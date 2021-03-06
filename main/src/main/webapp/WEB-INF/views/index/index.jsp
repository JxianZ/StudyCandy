<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>StudyCandy</title>
    <link href="${__static__}/css/main/bootstrap.min.css" rel="stylesheet">
    <link href="${__static__}/css/main/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${__static__}/css/main/common.css">
    <link rel="stylesheet" type="text/css" href="${__static__}/css/index/index.min.css">
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<%@include file="../include/header.jsp"%>

<!-- xs-download-html start -->

    <!--<div class="container-fluid visible-xs navbar-fixed-bottom">-->
        <!--快来下载APP呀,点击就送-->
    <!--</div>-->

<!-- xs-download-html start -->

<!-- ************************************************************************************************************** -->

<!-- banner-html start -->

<div class="container-fluid banner-min">
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            <li data-target="#carousel-example-generic" data-slide-to="3"></li>
            <li data-target="#carousel-example-generic" data-slide-to="4"></li>
        </ol>

        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="${__static__}/img/banner/0.jpg" alt="...">
                <div class="carousel-caption">

                </div>
            </div>
            <div class="item">
                <img src="${__static__}/img/banner/1.jpg" alt="...">
                <div class="carousel-caption">

                </div>
            </div>
            <div class="item">
                <img src="${__static__}/img/banner/2.jpg" alt="...">
                <div class="carousel-caption">

                </div>
            </div>
            <div class="item">
                <img src="${__static__}/img/banner/3.jpg" alt="...">
                <div class="carousel-caption">

                </div>
            </div>
            <div class="item">
                <img src="${__static__}/img/banner/4.jpg" alt="...">
                <div class="carousel-caption">

                </div>
            </div>

        </div>

            <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>

<!-- banner-html end -->

<!-- ************************************************************************************************************** -->

<!-- entry-html start -->

<div class="container">
    <div class="entry-wrapper">
        <h1 class="mod-title">校园入口</h1>
        <div class="row entry-inner">

            <div class="col-md-4 col-sm-4 entry-item">
                <div class="entry-img">
                    <img src="${__static__}/img/entry-class.jpg">
                </div>
                <h3>大教室</h3>
                <p  style="color: #000;">同学大牛为你们提供的各类课程</p>
                <a class="entry-button" href="#">进入</a>
            </div>

            <div class="col-md-4 col-sm-4 entry-item">
                <div class="entry-img">
                    <img src="${__static__}/img/entry-coach.jpg">
                </div>
                <h3>辅导室</h3>
                <p style="color: #000;">有不会的学霸一对一辅导，支持线下哦</p>
                <a class="entry-button" href="#">进入</a>
            </div>

            <div class="col-md-4 col-sm-4 entry-item">
                <div class="entry-img">
                    <img src="${__static__}/img/entry-square.jpg">
                </div>
                <h3>校园广场</h3>
                <p style="color: #000;">校园各类资讯动态，以及属于你的小秘密</p>
                <a class="entry-button" href="#">进入</a>
            </div>

        </div>
    </div>
</div>

<!-- entry-html end -->

<!-- ************************************************************************************************************** -->

<!-- video-html end -->

<div class="container">
    <div class=" video-wrapper">
        <h1 class="mod-title">热门视频</h1>
        <div class="row">
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item.png"></a>
                    <h3>域控服务器搭建</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item6.png"></a>
                    <h3>时间管理</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item2.png"></a>
                    <h3>PS中阶技巧</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item3.png"></a>
                    <h3>C++基础</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item4.png"></a>
                    <h3>沙盘训练</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item5.png"></a>
                    <h3>量化投资</h3>
                </div>
            </div>
        </div>
        <div class="row hidden-xs">
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item.png"></a>
                    <h3>域控服务器搭建2</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item6.png"></a>
                    <h3>时间管理2</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item2.png"></a>
                    <h3>PS中阶技巧2</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item3.png"></a>
                    <h3>C++基础2</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item4.png"></a>
                    <h3>沙盘训练2</h3>
                </div>
            </div>
            <div class="col-md-2 col-sm-2 col-xs-6">
                <div class=" video-item">
                    <a href="#"><img src="${__static__}/img/video/item5.png"></a>
                    <h3>量化投资2</h3>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- video-html end -->

<!-- ************************************************************************************************************** -->

<!-- square-html start -->

<div class="square-wrapper hidden-xs">
    <div class="container">
        <h1 class="mod-title">校园广场</h1>
        <div class="row">
            <div class="col-md-8 col-xs-12 square-body">
                <ul>

                    <li id="postList">
                        <a href="/square" style="text-decoration: none;"><div class="square-button-wrapper" id="square-more">查看全部帖子</div></a>
                    </li>
                </ul>
            </div>
            <div class="col-md-4 hidden-xs hidden-sm square-ad">
                <img src="${__static__}/img/ad.jpg">
            </div>
        </div>
    </div>
</div>

<!-- square-html end -->

<%@include file="../include/footer.jsp"%>
<!-- ************************************************************************************************************** -->

<!--  js file  -->
<script src="${__static__}/js/jquery.js"></script>
<script src="${__static__}/js/bootstrap.min.js"></script>
<script src="${__static__}/js/carousel.js"></script>
<script src="${__static__}/js/style-assit.js"></script>
<!-- 实现首页校园广场数据读取  未完成 js -->
<script type="text/javascript">
    function add0(m){return m<10?'0'+m:m }
    function format(timestamp)
    {
        //timestamp是整数，否则要parseInt转换,不会出现少个0的情况

        var time = new Date(timestamp);
        var year = time.getFullYear();
        var month = time.getMonth()+1;
        var date = time.getDate();
        var hours = time.getHours();
        var minutes = time.getMinutes();
        var seconds = time.getSeconds();
        return year+'-'+add0(month)+'-'+add0(date)+' '+add0(hours)+':'+add0(minutes)+':'+add0(seconds);
    }
    window.onload = function() {
        $.ajax({
            type: "GET",
            url: "/square/postList",
            dataType:"json",
            success: function (r) {
                console.log(r.data);
                for(var key in r.data){
                    $("#postList").before(

                        "<li class='topli'> <div class='navbar-left'> <h6>校园</h6><h5>"+r.data[key].postTitle+"</h5> </div> <div class='navbar-right'> <h6>999</h6> <h6>"+format(r.data[key].gmtCreate)+"</h6> </div> </li>"
                    );
                }
            },
            error: {}
        });
    };
</script>


</body>
</html>