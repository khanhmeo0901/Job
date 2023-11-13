package org.example.Code.service.Impl;

import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.example.Code.base.BaseAbstract;
import org.example.Code.entity.KetQua;
import org.example.Code.entity.ListObjectKeyWord;
import org.example.Code.entity.ObjectKeyWord;
import org.example.Code.entity.Product;
import org.example.Code.service.ELKService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ELKServiceImpl extends BaseAbstract implements ELKService {
    private final RestHighLevelClient client;
    public ELKServiceImpl(RestHighLevelClient client, RestHighLevelClient client1) {
        super(client);
        this.client = client1;
    }

    @Override
    public void testELK(String folder) {
        try{
            Gson gson = new Gson();

            List<Product> listProduct = Arrays.asList(
                    new Product("1","khanh","HN"),
                    new Product("2","khanh","Vp"),
                    new Product("3","khanh","HT")
            );

            BulkRequest bulkRequest = new BulkRequest();
            for (Product product : listProduct) {
                IndexRequest indexRequest = new IndexRequest("test4")
                        .id(product.getId())
                        .source(gson.toJson(product), XContentType.JSON);

                bulkRequest.add(indexRequest);
            }

            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

            // Process the response
            if (bulkResponse.hasFailures()) {
                // Handle failure cases
                System.err.println("Bulk request failed: " + bulkResponse.buildFailureMessage());
            } else {
                // Handle successful cases
                System.out.println("Bulk request successful");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFileFromELK(String fileName) {

    }

    @Override
    public KetQua getDataFromELk(String keyword, int from, int size, boolean and, boolean not, boolean or) {
        try {

            KetQua ketQua = new KetQua();
            Map<String,List<ListObjectKeyWord>> kq = new HashMap<>();
            SearchRequest request = new SearchRequest();
            request.indices("test");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("value",keyword));
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            if(and) {
                boolQueryBuilder.mustNot(QueryBuilders.matchQuery("value",keyword));
            }
            if(not) {
                boolQueryBuilder.must(QueryBuilders.matchQuery("value",keyword));
            }
            if(or) {
                boolQueryBuilder.should(QueryBuilders.matchQuery("value",keyword));
            }

            searchSourceBuilder.from((from-1)*size);
            searchSourceBuilder.size(size);
            request.source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            if (response.getHits().getTotalHits().value > 0) {
                List<ListObjectKeyWord> list = new ArrayList<>();
                SearchHit[] searchHit = response.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    ListObjectKeyWord listObjectKeyWord = new ListObjectKeyWord();
                    List<ObjectKeyWord> data = new ArrayList<>();
                    Object ob = map.get("value");
                    ArrayList<Object> arrayListValue = (ArrayList<Object>) ob;
                    for (Object item : arrayListValue) {
                        ObjectKeyWord objectKeyWord = new ObjectKeyWord();
                        String itemText = String.valueOf(item);
                        if (itemText.toLowerCase().contains(keyword.toLowerCase())) {
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
                    listObjectKeyWord.setList(data);
                    listObjectKeyWord.setId(hit.getId());
                    list.add(listObjectKeyWord);
                }
                kq.put("data",list);
            }
            ketQua.setListData(kq);
            ketQua.setTotal(String.valueOf(response.getHits().getTotalHits()));
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
