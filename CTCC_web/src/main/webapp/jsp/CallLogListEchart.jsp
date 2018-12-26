<%--
  Created by IntelliJ IDEA.
  User: Z
  Date: 2017/10/28
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>显示通话记录</title>
    <script type="text/javascript" src="../js/echarts.min.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-3.2.0.min.js"></script>--%>
    <%--<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>--%>
</head>
<body style="height: 100%; margin: 0; background-color: #3C3F41">
<style type="text/css">
    h3 {
        font-size: 12px;
        color: #ffffff;
        display: inline
    }
</style>
<h4 style="color: #ffffff;text-align:center">通话月单查询：${requestScope.name}</h4>
<%--<h3 style="margin-left: 70%">通话次数</h3>--%>
<%--<h3 style="margin-left: 20%">通话时长</h3>--%>
<div id="container1" style="height: 80%; width: 50%; float:left"></div>
<div id="container2" style="height: 80%; width: 50%; float:right"></div>
<script type="text/javascript">
    var telephone = "${requestScope.telephone}"
    var name = "${requestScope.name}"

    var date = "${requestScope.date}"//1月,2月,3月,xxxxx
    var count = "${requestScope.count}"

    var duration = "${requestScope.duration}"
    var pieData = converterFun(duration.split(","), date.split(","))
    callog1();
    callog2();

    function converterFun(duration, date) {
        var array = [];
        for (var i = 0; i < duration.length; i++) {
            var map = {};
            map['value'] = parseFloat(duration[i]);
            map['name'] = date[i];
            array.push(map);
        }
        return array;
    }

    function callog1() {
        var dom = document.getElementById("container1");
        var myChart = echarts.init(dom);
        myChart.showLoading();
        var option = {
            title: {
                text: '通话次数',
                textStyle: {
                    //文字颜色
                    color: '#ffffff',
                    //字体风格,'normal','italic','oblique'
                    fontStyle: 'normal',
                    //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                    fontWeight: 'bold',
                    //字体系列
                    fontFamily: 'sans-serif',
                    //字体大小
                    fontSize: 13
                },
                itemGap: 12,
            },
            grid: {
                x: 80,
                y: 60,
                x2: 80,
                y2: 60,
                backgroundColor: 'rgba(0,0,0,0)',
                borderWidth: 1,
                borderColor: '#ffffff'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                borderColor: '#ffffff',
                itemGap: 10,
                data: ['通话次数'],
                textStyle: {
                    color: '#ffffff'// 图例文字颜色
                }
            },
            toolbox: {
                show: false,
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    dataView: {readOnly: false},
                    magicType: {type: ['line', 'bar']},
                    restore: {},
                    saveAsImage: {}
                }
            },
            xAxis: {
                data: date.split(","),
                axisLine: {
                    lineStyle: {
                        color: '#ffffff',
                        width: 2
                    }
                }
            },
            yAxis: {
                axisLine: {
                    lineStyle: {
                        color: '#ffffff',
                        width: 2
                    }
                }
            },
            series: [
                {
                    type: 'line',
                    data: count.split(","),
                    itemStyle: {
                        normal: {
                            color: '#ffca29',
                            lineStyle: {
                                color: '#ffd80d',
                                width: 2
                            }
                        }
                    },
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
        myChart.hideLoading()
    }

    function callog2() {
        var dom = document.getElementById("container2");
        var myChart = echarts.init(dom);
        myChart.showLoading();
        var option = {
            title: {
                text: '通话时长',
                textStyle: {
                    //文字颜色
                    color: '#ffffff',
                    //字体风格,'normal','italic','oblique'
                    fontStyle: 'normal',
                    //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
                    fontWeight: 'bold',
                    //字体系列
                    fontFamily: 'sans-serif',
                    //字体大小
                    fontSize: 13
                },
                itemGap: 12,
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            visualMap: {
                show: false,
                min: Math.min.apply(null, duration.split(",")),
                max: Math.max.apply(null, duration.split(",")),
                inRange: {
                    colorLightness: [0, 0.5]
                }
            },
            series: [
                {
                    name: '通话时长',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '50%'],
                    data: pieData.sort(function (a, b) {
                        return a.value - b.value;
                    }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#01c1c2',
                            shadowBlur: 200,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
        myChart.hideLoading()
    }
</script>
</body>
</html>
