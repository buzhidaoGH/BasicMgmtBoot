;(function () {
  layui.use(['layer', 'jquery', 'table'], function () {
    const $ = layui.$
      , layer = layui.layer
      , table = layui.table
      , loadId = utils.loadingFun()
    const cpuTable = table.render({
      elem: '#cpuTable', skin: 'line',
      cols: [[
        {field: 'property', title: '属性', unresize: true},
        {field: 'value', title: '值', unresize: true}
      ]], data: []
    }), ticketTable = table.render({
      elem: '#ticketTable', skin: 'line',
      cols: [[
        {field: 'property', title: '属性', unresize: true},
        {field: 'memory', title: '内存', unresize: true},
        {field: 'jvm', title: 'JVM', unresize: true}
      ]], data: []
    })
    $.ajax({url: ctxUrl + '/server/server_info', method: 'post'})
      .done(({data: {diskCpuInfo, memoryInfo, serverInfo, jvmInfo}}) => {
        cpuTable.reload({data: diskCpuInfo})
        ticketTable.reload({data: memoryInfo})
        for (let key of Object.keys(serverInfo)) {$(`td[class=${ key }]`).html(serverInfo[key])}
        for (let key of Object.keys(jvmInfo)) {$(`td[class=${ key }]`).html(jvmInfo[key])}
      })
      .fail(function () { window.location.reload()})
      .always(function () {layer.close(loadId)})
  })
})()