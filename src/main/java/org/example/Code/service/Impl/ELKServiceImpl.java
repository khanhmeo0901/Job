package org.example.Code.service.Impl;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.example.Code.base.BaseAbstract;
import org.example.Code.entity.KetQua;
import org.example.Code.entity.ObjectKeyWord;
import org.example.Code.service.ELKService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ELKServiceImpl extends BaseAbstract implements ELKService {
    private RestHighLevelClient client;
    public ELKServiceImpl(RestHighLevelClient client, RestHighLevelClient client1) {
        super(client);
        this.client = client1;
    }
    @Override
    public KetQua getDataFromELk(String keyword, int from, int size) {
        try {
            KetQua ketQua = new KetQua();
            Map<String,List<ObjectKeyWord>> kq = new HashMap<>();
            List<ObjectKeyWord> data = new ArrayList<>();

            SearchRequest request = new SearchRequest();
            request.indices("test");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.from((from-1)*size);
            searchSourceBuilder.size(size);
            request.source(searchSourceBuilder);

            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = response.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    Object ob = map.get("value");
                    ArrayList<Object> arrayListValue = (ArrayList<Object>) ob;
                    for (Object item : arrayListValue) {
                        ObjectKeyWord objectKeyWord = new ObjectKeyWord();
                        String itemText = String.valueOf(item);
                        if (itemText.contains(keyword)) {
                            if (arrayListValue.indexOf(item) > 0) {
                                String previousLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) - 1));
                                objectKeyWord.setPreviousLine(previousLine);
                            }
                            String highlightedText = highlightKeywords(itemText, keyword);
                            objectKeyWord.setHighlightLine(highlightedText);
                            if (arrayListValue.indexOf(item) < arrayListValue.size() - 1) {
                                String nextLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) + 1));
                                objectKeyWord.setNextLine(nextLine);
                            }
                            data.add(objectKeyWord);
                        }
                    }
                }
            }
            ketQua.setTotal(String.valueOf(response.getHits().getTotalHits()));
            kq.put("data",data);
            ketQua.setListData(kq);
            return ketQua;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    public String highlightKeywords(String text, String keyword) {
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, "<mark>" + matcher.group() + "</mark>");
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
