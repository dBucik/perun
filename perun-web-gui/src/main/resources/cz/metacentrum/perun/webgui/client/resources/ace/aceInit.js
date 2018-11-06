var editor = ace.edit("editor");
console.log(editor);
editor.setTheme("ace/theme/dawn");
editor.getSession().setMode("ace/mode/json");
editor.setOptions({
	highlightActiveLine: true,
    autoScrollEditorIntoView: true,
	showGutter: false,
	fontSize: 16
});
editor.resize();

var inputQuery = document.getElementById('inputQuery');
editor.getSession().on('change', function(){
    inputQuery.innerText = editor.getSession().getValue();
});