class Comment extends React.Component {
    handleClick(event) {
        alert(this.props.content);
    }
    render() {
        return (
            <div className="col-md-4">
                <h2>{ this.props.author }</h2>
                <p>{ this.props.content } </p>
                <p><a className="btn btn-default" href="javascript:void(0);" role="button" onClick={ this.handleClick.bind(this) }>View details &raquo;</a></p>
            </div>
         );
    }
}

Comment.propTypes = {
    author: React.PropTypes.string,
    content: React.PropTypes.string
};