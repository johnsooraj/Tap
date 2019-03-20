import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {

  transform(
    value: string,
    limit: number = 20,
    completeWords: boolean = false,
    ellipsis: string = '...'
  ): string {
    let newLimit: number;
    const newValue: string = value.substr(0, limit);
    if (value === newValue) {
      return value;
    } else if (completeWords) {
      newLimit = newValue.lastIndexOf(' ');
      limit = newLimit > -1 ? newLimit : limit;
    }
    return value.substr(0, limit) + ellipsis;
  }

}
