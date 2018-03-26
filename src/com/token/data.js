var data = {
    "keyword": "wallet",
    "7daySale": 1688,
    "7dayCollection": 11914,
    "averageScore": 4.87,
    "averageGoodRate": 98.2,
    "statisticsTime": "10.1-10.7",
    "saleRank": [
        {
            "title": "2017 New Fashion Women....",
            "name": "产品1",
            "currentPrice": 7.76,
            "7daySale": 307,
            "marketShare": 18,
            "myData": {
                "totalSales": 307,
                "goodRate": 98.09852,
                "goodScore": 4.861419,
                "collection": 390
            },
            "marketData": {
                "totalSales": 808,
                "goodRate": 90.89,
                "goodScore": 4.253,
                "collection": 456
            }

        },
        {
            "title": "2017 New Fashion Women....",
            "name": "产品5",
            "currentPrice": 7.76,
            "7daySale": 307,
            "marketShare": 18,
            "myData": {
                "totalSales": 307,
                "goodRate": 98.09852,
                "goodScore": 4.861419,
                "collection": 390
            },
            "marketData": {
                "totalSales": 808,
                "goodRate": 90.89,
                "goodScore": 4.253,
                "collection": 456
            }

        },
        {
            "title": "2017 New Fashion Women....",
            "name": "产品9",
            "currentPrice": 7.76,
            "7daySale": 307,
            "marketShare": 18,
            "myData": {
                "totalSales": 307,
                "goodRate": 98.09852,
                "goodScore": 4.861419,
                "collection": 390
            },
            "marketData": {
                "totalSales": 808,
                "goodRate": 90.89,
                "goodScore": 4.253,
                "collection": 456
            }

        },
        {
            "title": "2017 New Fashion Women....",
            "name": "产品13",
            "currentPrice": 7.76,
            "7daySale": 307,
            "marketShare": 18,
            "myData": {
                "totalSales": 307,
                "goodRate": 98.09852,
                "goodScore": 4.861419,
                "collection": 390
            },
            "marketData": {
                "totalSales": 808,
                "goodRate": 90.89,
                "goodScore": 4.253,
                "collection": 456
            }

        },
        {
            "title": "2017 New Fashion Women....",
            "name": "产品15",
            "currentPrice": 7.76,
            "7daySale": 307,
            "marketShare": 18,
            "myData": {
                "totalSales": 307,
                "goodRate": 98.09852,
                "goodScore": 4.861419,
                "collection": 390
            },
            "marketData": {
                "totalSales": 808,
                "goodRate": 90.89,
                "goodScore": 4.253,
                "collection": 456
            }

        }
    ],
    "priceChanges": [
        {
            "name": "产品1",
            "changesDetail": [
                {"date": "2017-10-07", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-06", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-05", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-04", "originalPrice": 8.76, "newPrice": 68.5}
            ]
        },
        {
            "name": "产品3",
            "changesDetail": [
                {"date": "2017-10-07", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-06", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-05", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-04", "originalPrice": 8.76, "newPrice": 68.5}
            ]
        },
        {
            "name": "产品2",
            "changesDetail": [
                {"date": "2017-10-07", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-06", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-05", "originalPrice": 8.76, "newPrice": 68.5},
                {"date": "2017-10-04", "originalPrice": 8.76, "newPrice": 68.5}
            ]
        }
    ]
}

// 向后台获取数据
$.get('/data', function (data) {
    // ............其他操作

    // 图表操作
    data.saleRank.forEach(function (item) {
        rendChart(item);
    })

    // ............其他操作
});

function rendChart(id, data) {
    // 获取指定图表
    var chart = document.getElementById(id);
    // 渲染图表
    chart.createChart(data);
    // .................

}
