package demo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ArtistController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Artist.list(params), model:[artistCount: Artist.count()]
    }

    def show(Artist artist) {
        respond artist
    }

    def create() {
        respond new Artist(params)
    }

    @Transactional
    def save(Artist artist) {
        if (artist == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (artist.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond artist.errors, view:'create'
            return
        }

        artist.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'artist.label', default: 'Artist'), artist.id])
                redirect artist
            }
            '*' { respond artist, [status: CREATED] }
        }
    }

    def edit(Artist artist) {
        respond artist
    }

    @Transactional
    def update(Artist artist) {
        if (artist == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (artist.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond artist.errors, view:'edit'
            return
        }

        artist.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'artist.label', default: 'Artist'), artist.id])
                redirect artist
            }
            '*'{ respond artist, [status: OK] }
        }
    }

    @Transactional
    def delete(Artist artist) {

        if (artist == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        artist.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'artist.label', default: 'Artist'), artist.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
