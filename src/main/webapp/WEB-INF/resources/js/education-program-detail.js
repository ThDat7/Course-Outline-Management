$(() => {
    let courseContentIds = $('.courses-content div').map(function () {
        return $(this).data('id')
    }).get();

    $('select.courseId option').each(function () {
        if (courseContentIds.indexOf(parseInt($(this).val())) !== -1)
            $(this).remove()
    })

    $('select.courseId').selectpicker('destroy')
    $('select.courseId').selectpicker()

    mapClickEvent()
})

function createEducationProgramDivElement(id, name) {
    return `<div class="list-group-item" data-id="${id}">${name}</div>`
}

function createEducationInputElement(id, semester) {
    return `<input name="courses" type="hidden" data-id="${id}" value="${semester}-${id}">`
}

function createOptionElement(id, name) {
    return `<option value="${id}" data-name="${name}">${name}</option>`
}

function mapClickEvent() {
    $(".courses-content div").off("click")
    $(".courses-content div").click((e) => {
        $(".courses-content div").removeClass("selected")
        $(e.target).addClass("selected")
    })
}


$(".add-education-program").click((e) => {
    e.preventDefault()
    let semester = $('select.semester').val()
    let courses = $('select.courseId').find(":selected").map(function () {
        return {'id': parseInt($(this).val()), 'name': $(this).data("name")}
    }).get()


    let courseContentIds = $('.courses-content div').map(function () {
        return $(this).data('id')
    }).get();

    let isNotExist = courseContentIds.every(id => courses.map(e => e.id).indexOf(id) === -1)

    if (!isNotExist)
        return

    let courseInputElements = courses.map(course => {
        return createEducationInputElement(course.id, semester)
    })
    $('.input-courses').append(courseInputElements.join(''))

    let courseDivElement = courses.map(course => {
        return createEducationProgramDivElement(course.id, course.name)
    })
    $(`.semester-${semester} .courses-content`).append(courseDivElement.join(''))

    courses.map(e => {
        $(`select.courseId option[value=${e.id}]`).remove()
    })
    $('select.courseId').selectpicker('destroy')
    $('select.courseId').selectpicker()


    mapClickEvent()
})

$(".delete-education-program").click((e) => {
    e.preventDefault()
    if ($(".courses-content div.selected").length !== 1)
        return

    let id = $(".courses-content div.selected").data("id")
    $(`input[name='courses'][data-id=${id}]`).remove()
    $("select.courseId").append(createOptionElement(id, $(".courses-content div.selected").text()))
    $('select.courseId').selectpicker('destroy')
    $('select.courseId').selectpicker()

    $(".courses-content div.selected").remove()
})

$(".remove-course-outline").click((e) => {
    e.preventDefault()
    if ($(".courses-content div.selected").length !== 1)
        return

    let educationProgramId = location.pathname.split('/').slice(-1)
    let context = location.pathname.split('/')[1]
    let courseId = $(".courses-content div.selected").data("id")
    $.ajax({
        type: 'GET',
        url: `/${context}/api/education-programs/remove-outline/${educationProgramId}/${courseId}`,
        success: function () {
            let courseName = $(".courses-content div.selected a").text()
            if (courseName)
                $(".courses-content div.selected").html(courseName)
        }
    });
})