;(function () {
  layui.use(['layedit', 'layer'], function () {
    const layedit = layui.layedit,
      layer = layui.layer
    let editId = layedit.build('htmlEditing') //建立编辑器
      , loadId = utils.loadingFun()
    layedit.setContent(editId
      , `<b>BasicMgmtBoot</b>是由<a href='https://github.com/buzhidaoGH' target='_blank'>不知道更好</a>对各种项目的拙劣仿制。
<br/>0. 若依后台的部分思维，部分layuimini的页面布局，部分estate-portal项目的页面布局，途中遇到的问题参考了N位路人的文章
<br/>1. 其面向目标是简单的后台管理框架（没有内置过多复杂的其他库，简便，简便，还是简便）。
<br/>2. 其核心功能完善，可以在此基础上个性化开发。
<br/>作者为何又造一个“轮子”？个人拙见：
<br/>1. 对于小项目来说若依有多余的枝叶，无论是定时器，redis，权限管理，安全框架等等，大部分功能可能开发完后都用不到（但是运行时却需要附带运行）。
<br/>2. 对于大项目来说，都是有内部的“框架”，也不咋用别人的“轮子”，或者可以直接改别人的“轮子”。
<br/>3. 找框架用的大多数是处于刚接触SpringBoot，太复杂以至于“若依都无法运行”（作者在若依群里见过很多框架都运行不了的报错人在群里寻找帮助）。
<br/><b><span style="color: red">4. 以上纯属扯淡凑字数，主要还是自用</span></b>
<br/>开发心路历程：本来是想完成一个动态侧边栏，然后就完善到此地步。
`)
    layer.close(loadId)
  })
})()