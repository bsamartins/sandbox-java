class CommentForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }
    handleSubmit(event) {

        event.preventDefault();

        var author = this.state.author.trim();
        var content = this.state.content.trim();

        // validate
        if (!content || !author) {
            return false;
        }

        this.props.onCommentSubmit({author: author, content: content});
        this.setState({ author: "", content: "" });
    }
    handleAuthorChange(e) {
        this.setState({author: e.target.value})
    }
    handleContentChange(e) {
        this.setState({content: e.target.value})
    }
    render() {
        console.log(this);
        return (
            <form ref="form" className="navbar-form navbar-right" onSubmit={ this.handleSubmit.bind(this) }>
                <div className="form-group">
                    <input ref="author" placeholder="Your name" className="form-control" value={this.state.author} onChange={this.handleAuthorChange.bind(this)} />
                </div>
                <div className="form-group">
                    <input ref="content" placeholder="Say something..." className="form-control " value={this.state.content} onChange={this.handleContentChange.bind(this)} />
                </div>
                <button type="submit" className="btn btn-success">Post comment</button>
            </form>
        );
    }
}