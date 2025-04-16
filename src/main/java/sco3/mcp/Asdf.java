package sco3.mcp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.Content;
import io.modelcontextprotocol.spec.McpSchema.JsonSchema;

class Asdf {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] argv) {

		StdioServerTransportProvider tProvider = (//
		new StdioServerTransportProvider() //
		);

		String schema = "{}";

		McpSyncServer server = McpServer.sync(tProvider).serverInfo("my-server", "1.0.0")
				.tool(new Tool("calculator", "Performs calculations", schema), //
						(exchange, args) -> {
							List<Content> list = List.of( //
									new McpSchema.TextContent(args + "0") //
							);
							System.out.println(list);
							return new CallToolResult(list, false);
						})
				.build();

		logger.info("""
				{"jsonrpc":"2.0","id":1,"method":"notifications/tools/list_changed"}
				""".replaceAll("\n", ""));
		
		

	}
}
