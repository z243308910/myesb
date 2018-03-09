package com.echounion.bossmanager.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.Assert;

/**
 * 制作zip压缩包
 * @author 胡礼波
 * 2012-12-17 下午04:52:28
 */
public class Zipper {
	private static final Logger log = Logger.getLogger(Zipper.class);

	/**
	 * 制作压缩包
	 * @author 胡礼波
	 * 2012-12-17 下午04:52:40
	 * @param out  输出流
	 * @param fileEntrys 要打包的文件
	 * @param encoding 编码
	 */
	public static void zip(OutputStream out, List<FileEntry> fileEntrys,String encoding) {
		new Zipper(out, fileEntrys, encoding);
	}

	/**
	 * 制作压缩包
	 * @author 胡礼波
	 * 2012-12-17 下午04:53:37
	 * @param out
	 * @param fileEntrys
	 */
	public static void zip(OutputStream out, List<FileEntry> fileEntrys) {
		new Zipper(out, fileEntrys, null);
	}

	protected Zipper(OutputStream out, List<FileEntry> fileEntrys,String encoding) {
		Assert.notEmpty(fileEntrys);
		long begin = System.currentTimeMillis();
		log.info("开始制作压缩包");
		try {
			try {
				zipOut = new ZipOutputStream(out);
				if (!StringUtils.isBlank(encoding)) {
					log.debug("using encoding:"+encoding);
					zipOut.setEncoding(encoding);
				} else {
					log.debug("using default encoding");
				}
				for (FileEntry fe : fileEntrys) {
					zip(fe.getFile(), fe.getFilter(), fe.getZipEntry(), fe.getPrefix());
				}
			} finally {
				zipOut.close();
			}
		} catch (IOException e) {
			throw new RuntimeException("制作压缩包时，出现IO异常！", e);
		}
		long end = System.currentTimeMillis();
		log.info("制作压缩包成功。耗时："+(end - begin)+"ms。");
	}

	/**
	 * 压缩文件
	 * @param srcFile 源文件
	 * @param pentry 父ZipEntry
	 * @param prefix 文件名前缀
	 * @throws IOException
	 */
	private void zip(File srcFile, FilenameFilter filter, ZipEntry pentry,String prefix) throws IOException {
		ZipEntry entry;
		if (srcFile.isDirectory()) {
			if (pentry == null) {
				entry = new ZipEntry(srcFile.getName());
			} else {
				entry = new ZipEntry(pentry.getName() + "/" + srcFile.getName());
			}
			File[] files = srcFile.listFiles(filter);
			for (File f : files) {zip(f, filter, entry, prefix);
			}
		} else {
			if (pentry == null) {
				entry = new ZipEntry(prefix + srcFile.getName());
			} else {
				entry = new ZipEntry(pentry.getName() + "/" + prefix
						+ srcFile.getName());
			}
			FileInputStream in;
			try {
				log.debug("读取文件："+srcFile.getAbsolutePath());
				in = new FileInputStream(srcFile);
				try {
					zipOut.putNextEntry(entry);
					int len;
					while ((len = in.read(buf)) > 0) {
						zipOut.write(buf, 0, len);
					}
					zipOut.closeEntry();
				} finally {
					in.close();
				}
			} catch (FileNotFoundException e) {
				throw new RuntimeException("制作压缩包时，源文件不存在："+ srcFile.getAbsolutePath(), e);
			}
		}
	}

	private byte[] buf = new byte[1024];
	private ZipOutputStream zipOut;

	public static class FileEntry {
		private FilenameFilter filter;
		private String parent;				//打包后的父类包
		private File file;
		private String prefix;				//打包后的文件前缀

		public FileEntry(String parent, String prefix, File file,FilenameFilter filter) {
			this.parent = parent;
			this.prefix = prefix;
			this.file = file;
			this.filter = filter;
		}

		public FileEntry(String parent, File file) {
			this.parent = parent;
			this.file = file;
		}

		public FileEntry(String parent, String prefix, File file) {
			this(parent, prefix, file, null);
		}

		public ZipEntry getZipEntry() {
			if (StringUtils.isBlank(parent)) {
				return null;
			} else {
				return new ZipEntry(parent);
			}
		}

		public FilenameFilter getFilter() {
			return filter;
		}

		public void setFilter(FilenameFilter filter) {
			this.filter = filter;
		}

		public String getParent() {
			return parent;
		}

		public void setParent(String parent) {
			this.parent = parent;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public String getPrefix() {
			if (prefix == null) {
				return "";
			} else {
				return prefix;
			}
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
	}
}
