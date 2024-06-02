$(document).ready(function () {
    $('.date').datepicker({format: 'yyyy-mm-dd'});

    $('.addAssessmentComponent').click(addAssessmentComponent)
});


let assessmentComponentCount = 0
let methodCount = 0

function addAssessmentComponent(e) {
    e.preventDefault()
    const currentCount = assessmentComponentCount++
    const table = document
        .getElementById('assessmentTable')
        .getElementsByTagName('tbody')[0]
    const newRow = table.insertRow()

    const componentCell = newRow.insertCell(0)
    const methodsCell = newRow.insertCell(1)
    const timeCell = newRow.insertCell(2)
    const cloCell = newRow.insertCell(3)
    const weightCell = newRow.insertCell(4)
    const actionsCell = newRow.insertCell(5)

    const addAssessmentBtn = $('<button>')
        .attr('class', 'btn btn-success addAssessmentMethod')
        .click((e) => addAssessmentMethod(e, currentCount))
        .text('Thêm method đánh giá')

    const deleteComponentBtn = $('<button>')
        .attr('class', 'btn btn-danger')
        .click(function rm(e) {
            e.preventDefault()
            $(this).closest('tr').remove()
        })
        .text('Xóa thành phần đánh giá')

    actionsCell.innerHTML = `
            <div class="input-group-append">
            </div>
        `
    actionsCell.appendChild(addAssessmentBtn[0])
    actionsCell.appendChild(deleteComponentBtn[0])

    componentCell.innerHTML = `<div class="input-group">
            <input type="text" class="form-control component-input" placeholder="Thành phần đánh giá">
        </div>`

    methodsCell.id = `methods-${currentCount}`
    timeCell.id = `time-${currentCount}`
    cloCell.id = `clo-${currentCount}`
    weightCell.id = `weight-${currentCount}`
}

function addAssessmentMethod(e, componentId) {
    e.preventDefault()
    const currentCount = methodCount++
    const methodId = `method-${currentCount}`

    const methodsCell = document.getElementById(`methods-${componentId}`)
    const timeCell = document.getElementById(`time-${componentId}`)
    const cloCell = document.getElementById(`clo-${componentId}`)
    const weightCell = document.getElementById(`weight-${componentId}`)

    const methodsCellElement = document.createElement('div')
    const timeCellElement = document.createElement('div')
    const cloCellElement = document.createElement('div')
    const weightCellElement = document.createElement('div')

    methodsCellElement.innerHTML = `
            <div id="${methodId}" class="input-group">
                <input type="text" class="form-control method-input" placeholder="Bài đánh giá"
                style="margin-bottom: 10px;"></div>`
    timeCellElement.innerHTML = `<div id="${methodId}" class="input-group">
                <input type="text" class="form-control time-input" placeholder="Thời điểm"
                style="margin-bottom: 10px;"></div>`
    cloCellElement.innerHTML = `<div id="${methodId}" class="input-group">
                <input type="text" class="form-control clo-input" placeholder="CLO"
                style="margin-bottom: 10px;"></div>`
    weightCellElement.innerHTML = `
                <div id="${methodId}" class="input-group">
                        <input type="text" class="form-control weight-input" placeholder="Tỷ lệ %"
                            style="margin-bottom: 10px;">
                        <div class="input-group-append">
                            <button class="btn btn-danger">Xóa</button>
                        </div>
                </div>`

    methodsCell.append(methodsCellElement)
    timeCell.append(timeCellElement)
    cloCell.append(cloCellElement)
    weightCell.append(weightCellElement)

    $(weightCellElement)
        .find(`.btn-danger`)
        .click((e) => removeAssessmentMethod(e, methodId))
}

function removeAssessmentMethod(e, methodId) {
    e.preventDefault()
    const methodElements = document.querySelectorAll(`#${methodId}`)
    methodElements.forEach((element) => element.remove())
}

$('form').submit(submitData)

function submitData(e) {
    e.preventDefault()
    const courseAssessments = []
    const rows = document.querySelectorAll('#assessmentTable tbody tr')

    rows.forEach((row) => {
        const component = row.querySelector('.component-input')
        if (component) {
            const methods = row.querySelectorAll('.method-input')
            const times = row.querySelectorAll('.time-input')
            const clos = row.querySelectorAll('.clo-input')
            const weights = row.querySelectorAll('.weight-input')

            const assessmentMethods = []

            methods.forEach((method, index) => {
                const assessment = {
                    method: method.value,
                    time: times[index].value,
                    clos: clos[index].value,
                    weightPercent: weights[index].value,
                }
                assessmentMethods.push(assessment)
            })

            courseAssessments.push({
                type: component.value,
                assessmentMethods: assessmentMethods,
            })
        }
    })

    const contextPath = window.location.pathname.split('/')[1]
    const currentId = parseInt(window.location.href.split('/').pop())
    const teacher = {id: parseInt($('select[name="teacher"]').val())}
    const course = {id: parseInt($('select[name="course"]').val())}
    const content = $('textarea[name="content"]').val()
    const status = $('select[name="status"]').val()
    const deadlineDate = $('input[name="deadlineDate"]').val()

    const data = {
        id: currentId,
        content,
        course,
        teacher,
        status,
        courseAssessments,
        deadlineDate
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: `/${contextPath}/api/course-outlines`,
        data: JSON.stringify(data),
        success: (res) => {
            window.location.href = `/${contextPath}/course-outlines/`
        }
    });
}