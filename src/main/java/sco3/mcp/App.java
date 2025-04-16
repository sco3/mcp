/*
 * This source file was generated by the Gradle 'init' task
 */
package sco3.mcp;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.Content;
import io.modelcontextprotocol.spec.McpSchema.JsonSchema;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;

public class App {

	public static void main(String[] args) {
		StdioServerTransportProvider tProvider = (//
		new StdioServerTransportProvider() //
		);

		var cap = ServerCapabilities.builder() //
				.resources(false, true) //
				.tools(true) //
				.prompts(false) //
				.logging() //
				.build();

		McpSyncServer server = McpServer//
				.sync(tProvider)//
				.serverInfo("my-server", "1.0.0")//
				.capabilities(cap)//
				.build();

		var schema = """
				{
				  "type" : "object",
				  "properties" : {
				    "a" : {
				      "type" : "number"
				    },
				    "b" : {
				      "type" : "number"
				    }
				  }
				}
				""";

		var tool = new McpServerFeatures.SyncToolSpecification(//
				new Tool("mux", "mux", schema), //
				(exchange, arguments) -> {
					List<Content> list = List.of(new McpSchema.TextContent(""));
					System.out.println(list);
					return new CallToolResult(list, false);

				});

		System.out.println("""
				{"jsonrpc":"2.0","id":1,"method":"notifications/tools/list_changed"}
				""");
		server.addTool(tool);

	}
}
