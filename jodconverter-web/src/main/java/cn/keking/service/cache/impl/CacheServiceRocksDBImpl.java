package cn.keking.service.cache.impl;

import cn.keking.service.cache.CacheService;
import org.artofsolving.jodconverter.office.OfficeUtils;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author chenjh
 * @time 2019/4/22 11:02
 */
@ConditionalOnExpression("'${cache.type:default}'.equals('default')")
@Service
public class CacheServiceRocksDBImpl implements CacheService {

    public static final String GET_FROM_ROCKS_DB_EXCEPTION = "Get from RocksDB Exception";
    public static final String PUT_INTO_ROCKS_DB_EXCEPTION = "Put into RocksDB Exception";

    static {
        RocksDB.loadLibrary();
    }

    private static final String DB_PATH = OfficeUtils.getHomePath() + File.separator + "cache";

    private static final int QUEUE_SIZE = 500000;

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheServiceRocksDBImpl.class);

    private final BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

    private RocksDB db;

    {
        try {
            db = RocksDB.open(DB_PATH);
            if (db.get(FILE_PREVIEW_PDF_KEY.getBytes()) == null) {
                Map<String, String> initPDFCache = new HashMap<>();
                db.put(FILE_PREVIEW_PDF_KEY.getBytes(), toByteArray(initPDFCache));
            }
            if (db.get(FILE_PREVIEW_IMGS_KEY.getBytes()) == null) {
                Map<String, List<String>> initIMGCache = new HashMap<>();
                db.put(FILE_PREVIEW_IMGS_KEY.getBytes(), toByteArray(initIMGCache));
            }
            if (db.get(FILE_PREVIEW_PDF_IMGS_KEY.getBytes()) == null) {
                Map<String, Integer> initPDFIMGCache = new HashMap<>();
                db.put(FILE_PREVIEW_PDF_IMGS_KEY.getBytes(), toByteArray(initPDFIMGCache));
            }
        } catch (RocksDBException | IOException e) {
            LOGGER.error("Unable to init RocksDB",e);
        }
    }


    @Override
    public void initPDFCachePool(Integer capacity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initIMGCachePool(Integer capacity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void initPdfImagesCachePool(Integer capacity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putPDFCache(String key, String value) {
        try {
            Map<String, String> pdfCacheItem = getPDFCache();
            pdfCacheItem.put(key, value);
            db.put(FILE_PREVIEW_PDF_KEY.getBytes(), toByteArray(pdfCacheItem));
        } catch (RocksDBException | IOException e) {
            LOGGER.error(PUT_INTO_ROCKS_DB_EXCEPTION ,e);
        }
    }

    @Override
    public void putImgCache(String key, List<String> value) {
        try {
            Map<String, List<String>> imgCacheItem = getImgCache();
            imgCacheItem.put(key, value);
            db.put(FILE_PREVIEW_IMGS_KEY.getBytes(), toByteArray(imgCacheItem));
        } catch (RocksDBException | IOException e) {
            LOGGER.error(PUT_INTO_ROCKS_DB_EXCEPTION ,e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> getPDFCache() {
        Map<String, String> result = new HashMap<>();
        try{
            result = (Map<String, String>) toObject(db.get(FILE_PREVIEW_PDF_KEY.getBytes()));
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION ,e);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getPDFCache(String key) {
        String result = "";
        try{
            Map<String, String> map = (Map<String, String>) toObject(db.get(FILE_PREVIEW_PDF_KEY.getBytes()));
            result = map.get(key);
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION ,e);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getImgCache() {
        Map<String, List<String>> result = new HashMap<>();
        try{
            result = (Map<String, List<String>>) toObject(db.get(FILE_PREVIEW_IMGS_KEY.getBytes()));
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION ,e);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getImgCache(String key) {
        List<String> result = new ArrayList<>();
        Map<String, List<String>> map;
        try{
            map = (Map<String, List<String>>) toObject(db.get(FILE_PREVIEW_IMGS_KEY.getBytes()));
            result = map.get(key);
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION ,e);
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer getPdfImageCache(String key) {
        Integer result = 0;
        Map<String, Integer> map;
        try{
            map = (Map<String, Integer>) toObject(db.get(FILE_PREVIEW_PDF_IMGS_KEY.getBytes()));
            result = map.get(key);
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION ,e);
        }
        return result;
    }

    @Override
    public void putPdfImageCache(String pdfFilePath, int num) {
        try {
            Map<String, Integer> pdfImageCacheItem = getPdfImageCaches();
            pdfImageCacheItem.put(pdfFilePath, num);
            db.put(FILE_PREVIEW_PDF_IMGS_KEY.getBytes(), toByteArray(pdfImageCacheItem));
        } catch (RocksDBException | IOException e) {
            LOGGER.error(PUT_INTO_ROCKS_DB_EXCEPTION ,e);
        }
    }

    @Override
    public void cleanCache() {
        try {
            cleanPdfCache();
            cleanImgCache();
            cleanPdfImgCache();
        } catch (IOException | RocksDBException e) {
            LOGGER.error("Clean Cache Exception" ,e);
        }
    }

    @Override
    public void addQueueTask(String url) {
        blockingQueue.add(url);
    }

    @Override
    public String takeQueueTask() throws InterruptedException {
        return blockingQueue.take();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getPdfImageCaches() {
        Map<String, Integer> map = new HashMap<>();
        try{
            map = (Map<String, Integer>) toObject(db.get(FILE_PREVIEW_PDF_IMGS_KEY.getBytes()));
        } catch (RocksDBException | IOException | ClassNotFoundException e) {
            LOGGER.error(GET_FROM_ROCKS_DB_EXCEPTION + e);
        }
        return map;
    }


    private byte[] toByteArray (Object obj) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        bytes = bos.toByteArray ();
        oos.close();
        bos.close();
        return bytes;
    }

    private Object toObject (byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj;
        ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
        ObjectInputStream ois = new ObjectInputStream (bis);
        obj = ois.readObject();
        ois.close();
        bis.close();
        return obj;
    }

    private void cleanPdfCache() throws IOException, RocksDBException {
        Map<String, String> initPDFCache = new HashMap<>();
        db.put(FILE_PREVIEW_PDF_KEY.getBytes(), toByteArray(initPDFCache));
    }

    private void cleanImgCache() throws IOException, RocksDBException {
        Map<String, List<String>> initIMGCache = new HashMap<>();
        db.put(FILE_PREVIEW_IMGS_KEY.getBytes(), toByteArray(initIMGCache));
    }

    private void cleanPdfImgCache() throws IOException, RocksDBException {
        Map<String, Integer> initPDFIMGCache = new HashMap<>();
        db.put(FILE_PREVIEW_PDF_IMGS_KEY.getBytes(), toByteArray(initPDFIMGCache));
    }
}
