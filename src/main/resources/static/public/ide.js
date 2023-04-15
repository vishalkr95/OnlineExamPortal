let editor;
let curl='http://localhost:9000';

var element = document.getElementById("Running");
$('.editor').each(function (index) {
    editor = ace.edit(this);
    console.log("hello");
    console.log(editor);
    editor.setTheme('ace/theme/monokai');
    editor.setHighlightActiveLine(false);
    editor.setShowPrintMargin(false);
    editor.getSession().setMode('ace/mode/c_cpp');
    if (this.classList.contains('edit1')) {
        let result;
        $.ajax({
            type: "get",
            crossDomain: true,
            url: '/getcode/cpp',
            async: false,
            contentType: "application/json",
            dataType: "json",
            success: function (response) {
                result = response['data'];
                console.log(result)
            },
            error: function (result) {
                console.log(result)
            }
        })
        editor.setOption("enableBasicAutocompletion", true);
        editor.setOption("enableSnippets", true);
        editor.setOption("enableLiveAutocompletion", true);
        editor.setValue(result);
        editor.clearSelection();
    }
    else if (this.classList.contains('edit2')) {
        editor.renderer.setShowGutter(false);
        editor.getSession().setMode('ace/mode/txt');
        editor.setValue("");
        editor.clearSelection();

    }
    else {
        editor.renderer.setShowGutter(false);
        editor.clearSelection();
        editor.getSession().setMode('ace/mode/txt');
        editor.setValue("");
        editor.clearSelection();
    }

});

$('a[href$="#Modal"]').on("click", function () {
    $('#Modal').modal('show');
});

function langSelector(lang) {
    $('.editor').each(function (index) {
        if (this.classList.contains('edit1')) {
            editor = ace.edit(this);
            editor.getSession().setMode(`ace/mode/${lang}`);
            let result;
            if (lang === 'c_cpp') {
                $.ajax({
                    type: "get",
                    crossDomain: true,
                    url: '/getcode/cpp',
                    async: false,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (response) {
                        result = response['data'];
                        console.log(result)
                    },
                    error: function (result) {
                        console.log(result)
                    }
                })


            }
            else if (lang === 'python') {
                $.ajax({
                    type: "get",
                    crossDomain: true,
                    url: '/getcode/py',
                    async: false,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (response) {
                        result = response['data'];
                        console.log(result)
                    },
                    error: function (result) {
                        console.log(result)
                    }
                })

            }
            else if (lang === 'java') {
                $.ajax({
                    type: "get",
                    crossDomain: true,
                    url: '/getcode/java',
                    async: false,
                    contentType: "application/json",
                    dataType: "json",
                    success: function (response) {
                        result = response['data'];
                        console.log(result)
                    },
                    error: function (result) {
                        console.log(result)
                    }
                })
            }
            editor.setValue(result);
        }
        editor.clearSelection();
    });

    $('#spanLang').text(lang);



}
function themeSelector(theme) {
    $('.editor').each(function (index) {
        editor = ace.edit(this);
        editor.setTheme(`ace/theme/${theme}`);
    });
    $('#spanTheme').text(theme);
}

function saveCode() {
    let mode = "##";
    let lang;
    $('.editor').each(function (index) {
        editor = ace.edit(this);
        const code = editor.getSession().getValue();
        if (mode === "##") {
            mode = editor.getSession().getMode()['$id'];
        }


        if (mode === 'ace/mode/c_cpp') {
            lang = 'cpp';
        }
        else if (mode === 'ace/mode/python') {
            lang = 'py';
        }
        else if (mode === 'ace/mode/java') {
            lang = 'java';
        }

        if (this.classList.contains('edit1')) {
            const tosend = {
                data: code
            }
            $.ajax({
                type: "post",
                crossDomain: true,
                url: `/savecode/${lang}`,
                contentType: "application/json",
                dataType: "json",               
                async: false,
                data: JSON.stringify(tosend),
                success: function (response) {
					
                    console.log(response, status)
                    console.log("hello from sucess");
                },
                error: function (result, status) {
					console.log("hello from save code error");
					console.log(result);
                    console.log(result, status)
                }
            })
        }
        else if (this.classList.contains('edit2')) {
            const tosend = {
                data: code
            }
            $.ajax({
                type: "post",
                crossDomain: true,
                url: `/saveinput/${lang}`,
                contentType: "application/json",
                dataType: "json",
                async: false,
                data: JSON.stringify(tosend),
                success: function (response) {
					console.log("hello from sucess");
                    console.log(response, status)
                },
                error: function (result, status) {
                    console.log(result, status)
                    console.log("hello from error");
					console.log(result);
                }
            })

        }
    });

    return lang;


}
function runCode() {
    element.textContent = "Compiling.."
    const lang = saveCode();
    let result;
    $.ajax({
        type: "get",
        crossDomain: true,
        url: `/run/${lang}`,
        async: false,
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            result = response['success'];
            console.log(result)
            console.log("hello from suss");
					
        },
        error: function (result) {
            console.log(result)
            result = "Something went wrong"
        }
    })

    // setTimeout(function () {
    $('.editor').each(function (index) {
        editor = ace.edit(this);
        if (this.classList.contains('edit3')) {
            editor.setValue(result);
            editor.clearSelection();
        }
    });
    element.textContent = "Run";
    showmsg();
    // }, 1000)
}



function showmsg() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function () { x.className = x.className.replace("show", ""); }, 1000);
}



var handler = document.querySelector('.handler');
var wrapper = handler.closest('.wrapper');
var boxA = wrapper.querySelector('.box');
var isHandlerDragging = false;

document.addEventListener('mousedown', function (e) {
    if (e.target === handler) {
        isHandlerDragging = true;
    }
});

document.addEventListener('mousemove', function (e) {
    if (!isHandlerDragging) {
        return false;
    }
    var containerOffsetLeft = wrapper.offsetLeft;
    var pointerRelativeXpos = e.clientX - containerOffsetLeft;
    var boxAminWidth = 60;
    boxA.style.width = (Math.max(boxAminWidth, pointerRelativeXpos - 8)) + 'px';
    boxA.style.flexGrow = 0;
});

document.addEventListener('mouseup', function (e) {
    isHandlerDragging = false;
});

function myFunction() {
    document.getElementById("mainFrameOne").style.display = "none";
    document.getElementById("mainFrameTwo").style.display = "block";
}



document.addEventListener("DOMContentLoaded", function () {

    el_autohide = document.querySelector('.autohide');

    // add padding-top to bady (if necessary)
    navbar_height = document.querySelector('.navbar').offsetHeight;
    document.body.style.paddingTop = navbar_height + 'px';

    if (el_autohide) {
        var last_scroll_top = 0;
        window.addEventListener('scroll', function () {
            let scroll_top = window.scrollY;
            if (scroll_top < last_scroll_top) {
                el_autohide.classList.remove('scrolled-down');
                el_autohide.classList.add('scrolled-up');
            }
            else {
                el_autohide.classList.remove('scrolled-up');
                el_autohide.classList.add('scrolled-down');
            }
            last_scroll_top = scroll_top;
        });
        // window.addEventListener
    }
    // if

});





