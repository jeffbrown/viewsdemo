import demo.Artist

class BootStrap {

    def init = { servletContext ->
        def kc = new Artist(name: 'King Crimson')
        kc.addToAlbums(title: 'Red')
        kc.addToAlbums(title: 'Discipline')
        kc.save()

        def jb = new Artist(name: 'Jeff Beck')
        jb.addToAlbums(title: 'Blow By Blow')
        jb.addToAlbums(title: 'Wired')
        jb.save()
    }
    def destroy = {
    }
}
