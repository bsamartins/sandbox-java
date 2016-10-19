class CommentList extends React.Component{
    constructor(props) {
        super(props);
        this.state = props;
    }
    componentDidMount() {
        var eventSource = new EventSource("/sse/updates");
        var self = this;
        eventSource.onmessage = function(e) {
            var comment = JSON.parse(e.data);
            var comments = self.state.comments;
            comments.push(comment);
            self.forceUpdate();
        };
    }
    render() {
        var commentNodes = this.state.comments.map(function ( comment ) {
            return <Comment author={ comment.author } content={ comment.content } key={ comment.id } />
        });

        return (
            <div className="comment-list">
                { commentNodes }
            </div>
        )
    }
}