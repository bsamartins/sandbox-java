var CommentForm = React.createClass({
    getInitialState: function() {
        return {};
    },
    handleSubmit: function ( event ) {

        event.preventDefault();

        var author = this.state.author.trim();
        var content = this.state.content.trim();

        // validate
        if (!content || !author) {
            return false;
        }

        this.props.onCommentSubmit({author: author, content: content});
        this.setState({ author: "", content: "" });
    },
    handleAuthorChange: function(e) {
        this.setState({ author: e.target.value });
    },
    handleContentChange: function(e) {
        this.setState({ content: e.target.value });
    },
    render: function () {
        return (
            <form ref="form" className="navbar-form navbar-right" onSubmit={ this.handleSubmit }>
                <div className="form-group">
                    <input ref="author" placeholder="Your name" className="form-control" value={this.state.author} onChange={this.handleAuthorChange} />
                </div>
                <div className="form-group">
                    <input ref="content" placeholder="Say something..." className="form-control " value={this.state.content} onChange={this.handleContentChange} />
                </div>
                <button type="submit" className="btn btn-success">Post comment</button>
            </form>
        )
    }
});