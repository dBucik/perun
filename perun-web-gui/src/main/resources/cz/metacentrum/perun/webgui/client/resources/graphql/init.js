var search = window.location.search;
var parameters = {
	query: null,
	variables: null,
	operationName: null
};

function onEditQuery(newQuery) {
	parameters.query = newQuery;
}
function onEditVariables(newVariables) {
	parameters.variables = newVariables;
}
function onEditOperationName(newOperationName) {
	parameters.operationName = newOperationName;
}

function graphQLFetcher(graphQLParams) {
	var path = window.location.pathname.split("/");
	var URL = window.location.protocol + "//" +
		window.location.host +
		window.location.port + '/' +
		path[1] + '/' + path[2].replace("gui", "rpc") +
		"/json/searcher/search";
	return fetch( URL, {
		method: 'GET',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(graphQLParams),
	}).then(response => response.json());
}

ReactDOM.render(
	React.createElement(GraphiQL, {
		fetcher: graphQLFetcher,
		query: parameters.query,
		variables: parameters.variables,
		operationName: parameters.operationName,
		editorTheme: "solarized"
	}),
	document.getElementById('graphiql')
);