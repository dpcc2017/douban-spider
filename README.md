# 豆瓣爬虫

豆瓣爬虫是一个基于爬取豆瓣各种信息的项目

## V0.0.1爬取豆瓣电影
  当前版本可以爬取豆瓣电影，后续计划添加音乐，书籍等爬虫功能，最终目标完成一个通用功能只需要一些简单的配置就可以爬取任何信息，甚至其他网站信息（注意：发现豆瓣反扒方式相当无解，无论你以什么方式去查询什么信息只展示前500，所以只能通过各种标签尽可能的爬取更多的信息）>
### 爬取的数据
 * 下面是爬取的电影信息（部分信息需要深度爬虫才能获取，本项目中该功能已实现，但暂时未去爬取）
![电影](https://github.com/shanyao19940801/douban-spider/blob/master/douban-spider/src/main/resources/img/movedata.PNG "豆瓣电影数据示例")
### 开发工具以及框架
 * java开发工具：IntelliJ IDEA
 * 项目管理：Maven
 * 版本管理：GitHub
 * 使用数据库： MYSQL5.7
 * 持久层框架：Mybatis
 * 第三方库
    <br>HttpClient4.5-网络请求
    <br>Jsoup-html标签解析
    <br>c3p0 数据库连接池
