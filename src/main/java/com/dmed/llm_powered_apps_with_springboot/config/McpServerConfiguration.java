package com.dmed.llm_powered_apps_with_springboot.config;

import com.dmed.llm_powered_apps_with_springboot.tool.HelpdeskTools;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for the MCP Server
 * This configures the Model Context Protocol server to expose the helpdesk tools
 */
@Configuration
public class McpServerConfiguration {

    /**
     * Configures the MCP Server to run over stdio and exposes the available tools
     */
    @Bean
    public List<ToolCallback> toolCallbacks(HelpdeskTools helpdeskTools) {
        return List.of(ToolCallbacks.from(helpdeskTools));
    }
}
