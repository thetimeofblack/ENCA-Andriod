# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class AmazonwebspiItem(scrapy.Item):
	good_name = scrapy.Field()		# 商品名称   
	good_description = scrapy.Field()#介绍-文字
	#good_instruction = scrapy.Field()#详情-图片
	pass
