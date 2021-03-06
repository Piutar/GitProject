import { Injectable } from '@angular/core';
import {IPost} from './ipost';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  // private readonly API_URL = 'http://jsonplaceholder.typicode.com/posts';
  private readonly API_URL = 'http://localhost:8080/';

  constructor(private http: HttpClient) { }
  getPosts(count = 10): Observable<IPost[]> {
    return this.http.get<IPost[]>(this.API_URL).pipe(
      map(response => response.filter((post, i) => i < count))
    );
  }
  getPostById(id: number): Observable<IPost> {
    return this.http.get<IPost>(`${this.API_URL}/${id}`);
  }
  createPost(post: Partial<IPost>): Observable<IPost> {
    return this.http.post<IPost>(`${this.API_URL}/create`, post);
  }
  deletePost(id: number): Observable<any> {
    return this.http.delete(`${this.API_URL}/delete/${id}`);
  }
  updatePost(post: IPost): Observable<IPost> {
    return this.http.put<IPost>(`${this.API_URL}/edit/${post.id}`, post);
  }
}
