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
    public KetQua getDataFromELk(String keyword) {
        try {
            KetQua ketQua = new KetQua();
            Map<String,List<String >> kq = new HashMap<>();
            List<String> data = new ArrayList<>();
            SearchRequest request = new SearchRequest();
            request.indices("test");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = response.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    Object ob = map.get("value");
                    ArrayList<Object> arrayListValue = (ArrayList<Object>) ob;
                    for (Object item : arrayListValue) {
                        String itemText = String.valueOf(item);
                        if (itemText.contains(keyword)) {
                            if (arrayListValue.indexOf(item) > 0) {
                                String previousLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) - 1));
                                System.out.println("Dòng trước: " + previousLine);
                                data.add(previousLine);
                            }
                            String highlightedText = highlightKeywords(itemText, keyword);
                            data.add(highlightedText);
                            System.out.println("Dòng chứa keyword được highlight: " + highlightedText);
                            if (arrayListValue.indexOf(item) < arrayListValue.size() - 1) {
                                String nextLine = String.valueOf(arrayListValue.get(arrayListValue.indexOf(item) + 1));
                                System.out.println("Dòng sau: " + nextLine);
                                data.add(nextLine);
                            }
                        }
                    }
                }
            }
            kq.put("data",data);
            ketQua.setData(kq);
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
