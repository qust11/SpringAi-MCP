package com.ym.service.impl;


import com.ym.service.IMcpService;
import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/30 9:30
 **/
@Service
public class McpServiceImpl implements IMcpService {
    @Autowired
    private List<McpSyncClient> mcpSyncClientList;

    @Override
    public void test(){
        McpSyncClient mcpSyncClient = mcpSyncClientList.get(0);
    }

}
