import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {YoutubeService} from '../youtube.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-youtube-player',
  templateUrl: './youtube-player.component.html',
  styleUrls: ['./youtube-player.component.css']
})
export class YoutubePlayerComponent implements OnInit, OnDestroy {
  song: any;
  sub: Subscription;

  constructor(
    private youtubeService: YoutubeService,
    private activatedRouter: ActivatedRoute,
    private domSanitizer: DomSanitizer
  ) {
  }

  ngOnInit() {
    this.sub = this.activatedRouter.paramMap.subscribe((paramMap: ParamMap) => {
      const id = paramMap.get('id');
      this.song = this.youtubeService.find(id);
    });
  }

  getSrc() {
    const url = 'https://www.youtube.com/embed/' + this.song.id + '?autoplay=1&playlist=GqC8jWzmiLA';
    return this.domSanitizer.bypassSecurityTrustResourceUrl(url);
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

}
