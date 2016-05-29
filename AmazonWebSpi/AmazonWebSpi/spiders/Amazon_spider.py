import sys
import imp
imp.reload(sys)
from scrapy.spider import Spider
from scrapy.selector import Selector
from scrapy.http import Request
from scrapy import log
import urllib.parse
from selenium import webdriver
import time
import sqlite3
from AmazonWebSpi.items import AmazonwebspiItem

class AmazonWebSpider(Spider):
	name = "AmazonWebSpider"
	download_delay = 6
	allowed_domains = ["amazon.de"]
	start_urls = [
		"https://list.jd.com/list.html?cat=1316,1625,1663",
	]
	origin_url = "https://list.jd.com"
	conn = sqlite3.connect('AmazonTest.db')
	cursor = conn.cursor()
	
	def __init__(self):
		Spider.__init__(self)
		self.browser = webdriver.Firefox()
		self.cursor.execute('create table if not exists CleaningAgents (cleaningAgentID int primary key, name varchar(20),description varchar(20),instruction varchar(20),application Time long,frequency long,cleaningAgentType varchar(20))')
		
	def __del__(self):
		self.browser.close()
		self.cursor.close()
		self.conn.commit()
		self.conn.close()
	
	def parse(self,response):
		sel = Selector(response)
		goods = sel.xpath("//*[@id=\"plist\"]/ul")
		for good in goods:
			good_name = good.xpath("li/div/div[3]/a/em/text()").extract()
			good_url=good.xpath("li/div/div[3]/a/@href").extract()
			yield Request("http:"+good_url[0],callback=self.parseItem,dont_filter=True)			
			
		nextlink = response.xpath("//*[@id=\"J_bottomPage\"]/span[1]/a[10]/@href").extract()
		if nextlink:
			link=nextlink[0]
			nexturl=self.origin_url+link
			yield Request(nexturl,callback=self.parse,dont_filter=True)
	
	
	
	def parseItem(self,response):
		item = AmazonwebspiItem()
		self.browser.get(response.url)
		time.sleep(13)
		sel = Selector(response)
		good_description = sel.xpath("//*[@id=\"parameter2\"]/li/text()").extract()
		item["good_name"]=[good_description[0].encode('utf-8')]
		item["good_description"]=[n.encode('utf-8') for n in good_description[1:len(good_description)-1]]
		description_temp = ""
		for n in good_description[1:len(good_description)-1]:
			description_temp += n
		self.cursor.execute('insert into CleaningAgents (name,descripition) values(good_description[0].encode(\'utf-8\'),description_temp)')
		self.conn.commit()
		yield item