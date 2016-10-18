ReactDOM.render(<CommentForm onCommentSubmit={ function(comment) {
 $.post('/', comment, null, 'json');
} }/>, document.getElementById("navbar"));

$.getJSON('/', function( data ) {
    ReactDOM.render(<CommentList comments={ data }/>, document.getElementById("comments"));
});
