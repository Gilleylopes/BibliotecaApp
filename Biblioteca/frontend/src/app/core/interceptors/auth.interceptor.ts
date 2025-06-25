import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthService } from 'src/app/auth/services/auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;

  constructor(private auth: AuthService, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();

    const authReq = token
      ? req.clone({ headers: req.headers.set('Authorization', `Bearer ${token}`) })
      : req;

    return next.handle(authReq).pipe(
      catchError((err) => {
        if (err instanceof HttpErrorResponse && err.status === 401) {
          const refreshToken = this.auth.getRefreshToken();
          if (!this.isRefreshing && refreshToken) {
            this.isRefreshing = true;

            return this.auth.refresh(refreshToken).pipe(
              switchMap((res: { accessToken: string }) => {
                this.auth.salvarTokens({
                  accessToken: res.accessToken,
                  refreshToken: this.auth.getRefreshToken()!
                });
                this.isRefreshing = false;

                const retryReq = req.clone({
                  headers: req.headers.set('Authorization', `Bearer ${res.accessToken}`)
                });
                return next.handle(retryReq);
              }),
              catchError((refreshErr) => {
                this.auth.logout();
                this.router.navigate(['/auth/login']);
                return throwError(() => refreshErr);
              })
            );
          }

          this.auth.logout();
          this.router.navigate(['/auth/login']);
        }

        return throwError(() => err);
      })
    );
  }
}
