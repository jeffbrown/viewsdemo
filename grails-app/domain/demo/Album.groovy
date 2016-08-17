package demo

class Album {
    String title

    static belongsTo = [artist: Artist]

    String toString() {
        title
    }
}
