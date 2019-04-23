package com.chanct.biz.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * 用于头像图片文件的保存
 * @author Administrator
 *
 */
@Service
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	//文件的本地保存路径
	@Value("${file.path}")
	private String filePath;
	
	//该注解是spring框架的注解, 该注解的方法会在项目启动的时候执行
	//??? 在spring IOC容器创建之后, bean初始化之前和销毁之前执行 ??? 这里做一个字符串处理应
	@PostConstruct
	public void topath(){
		//由于目录的分隔符根据系统区分   linux用 /  windows用 \  但是\ 转换的时候要用 \\\\ 转义
		filePath = filePath.replaceAll("[|]",File.separator.equals("/")?"/":"\\\\");
	}
	
	
	public List<String> getImgPath(List<MultipartFile> files){
//		String toFilePath = toFilePath(filePath);
		List<String> paths = Lists.newArrayList();
		files.forEach(file -> {
			File localFile = null;
			if (!file.isEmpty()) {
				try {
					localFile = saveToLocal(file,filePath);//保存头像到本地
					logger.info("file before = {}",localFile.getAbsolutePath());
					logger.info("file.1 = {} \n {}",localFile.getPath(),localFile.getCanonicalPath());
					String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
					logger.info("file path = {}",filePath);
					logger.info("file after = {}",path);
					logger.info("file ffff = {}   : {}",filePath,File.separator); 
					paths.add(path);//只存储一个相对路径
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		return paths;//返回文件路径列表
	}
	
	private File saveToLocal(MultipartFile file, String FilePath2) throws IOException {
		File newFile = new File(FilePath2 + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
		if(!newFile.exists()) {
			newFile.getParentFile().mkdirs();//逐级创建父目录
			newFile.createNewFile();//创建文件, 这个就是前端传递的直接文件名 abc.png
		}
		Files.write(file.getBytes(), newFile);//利用guava工具  将文件内容写入创建号的文件
		return newFile;
	}
	
}
