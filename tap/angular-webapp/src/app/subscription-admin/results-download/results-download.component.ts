import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import Chart from 'chart.js';
import * as jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { CommonLoginService } from 'src/app/common-login/common-login.service';
import { SubscriptionService } from '../subscription.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgProgress } from '@ngx-progressbar/core';
import { NgxNotificationService } from 'ngx-notification';
import { Router } from '@angular/router';

class CurrentModal {
  id: number;
  optionType: number;
  optionsRespons: any[];
  options: any[];
  createDate: any;
  rateCounts: any[];
}

@Component({
  selector: 'app-results-download',
  templateUrl: './results-download.component.html',
  styleUrls: ['./results-download.component.css']
})
export class ResultsDownloadComponent implements OnInit {
  subscriptionResults = [];
  chartGraph = [];
  myList = [];
  currentPage = 1;
  totalCount = 0;

  displayCommonData;
  displayListData;

  constructor(
    private commonService: CommonLoginService,
    private localService: SubscriptionService,
    private modalService: NgbModal,
    private progressbar: NgProgress,
    private toster: NgxNotificationService,
    private router: Router
  ) {}

  ngOnInit() {
    const limit = 10;
    for (let index = 0; index < limit; index++) {
      const element = limit[index];
      this.myList.push({ myid: 'hello' + index, mydata: null });
    }
    this.fetchResultsOnPageChange(1);
  }

  fetchResultsOnPageChange(page) {
    const fetch = 'all';
    this.localService.fetchSubscriptionResults(fetch, page).subscribe(result => {
      if (result[0]) {
        this.totalCount = result[0]['count']; // get total count
        result.splice(0, 1); // remove count object
      }
      console.log(result);
      this.subscriptionResults = result;
      const mainDiv = document.getElementById('result-container');
      //mainDiv.scrollTo({ left: 0, top: 0, behavior: 'smooth' });
      setTimeout(() => {
        this.setGraphWithListData();
      }, 0);
    });
  }

  setGraphWithListData() {
    this.subscriptionResults.forEach(obj => {
      if (obj.feedbackType && obj.feedbackType !== 4) {
        this.getPieChart(`feedGraph1${obj.feedbackId}`, obj);
      }
      if (obj.pollType && obj.pollType !== 4) {
        this.getPieChart(`pollGraph1${obj.pollId}`, obj);
      }
    });
  }

  getPieChart(id, data) {
    const randomScalingFactor = function() {
      return Math.round(Math.random() * 100);
    };
    const config = {
      type: 'pie',
      data: {
        datasets: [
          {
            data: this.getPieChartData(data),
            backgroundColor: ['#2CD9E9', '#E4E62D', '#E8A32C', '#2DE897', '#DF3A27', '#3C68B1'],
            label: 'Dataset 1'
          }
        ],
        labels: this.getPieChartlables(data)
      },
      options: {
        responsive: true,
        layout: {
          padding: {
            left: 0,
            right: 0,
            top: 50,
            bottom: 0
          }
        }
      }
    };
    const myChart = new Chart(id, config);
    return myChart;
  }

  getPieChartlables(data): string[] {
    const lables = [];
    if (data.pollType === 2 || data.feedbackType === 2) {
      if (data.questions && data.questions.length > 0) {
        data.questions.forEach((element, index) => {
          lables.push('Option ' + (index + 1));
        });
        return lables;
      }
    }

    if (data.pollType === 3 || data.feedbackType === 3) {
      lables.push('Rating 1');
      lables.push('Rating 2');
      lables.push('Rating 3');
      lables.push('Rating 4');
      lables.push('Rating 5');
    }

    if (data.pollImages && data.pollImages.length > 0) {
      data.pollImages.forEach((element, index) => {
        lables.push('Image ' + (index + 1));
      });
      return lables;
    }
    return lables;
  }

  getPieChartData(data): number[] {
    const values = [];
    if (data.questions && data.questions.length > 0) {
      data.questions.forEach((element, index) => {
        values.push(element.responses.length);
      });
      return values;
    }

    if (data.pollType === 3 || data.feedbackType === 3) {
      let one = 0;
      let two = 0;
      let three = 0;
      let four = 0;
      let five = 0;
      if (data.ratings && data.ratings.length > 0) {
        data.ratings.forEach(element => {
          if (element.ratingCount === 1) {
            one++;
          }
          if (element.ratingCount === 2) {
            two++;
          }
          if (element.ratingCount === 3) {
            three++;
          }
          if (element.ratingCount === 4) {
            four++;
          }
          if (element.ratingCount === 5) {
            five++;
          }
        });
      }
      return [one, two, three, four, five];
    }

    if (data.pollType === 5 || data.feedbackType === 5) {
      const dataarray = [];
      if (data.pollImages && data.pollImages.length > 0) {
        data.pollImages.forEach(element => {
          dataarray.push(element.imageRespons.length);
        });
      }
      return dataarray;
    }
  }

  getBarChart(id, data) {
    const config = {
      type: 'bar',
      data: {
        datasets: [
          {
            data: this.getPieChartData(data),
            backgroundColor: ['#2CD9E9', '#E4E62D', '#E8A32C', '#2DE897', '#DF3A27', '#3C68B1'],
            label: 'Dataset 1'
          }
        ],
        labels: this.getPieChartlables(data)
      },
      options: {
        responsive: true,
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true,
                callback: function(value) {if (value % 1 === 0) {return value;}}
              }
            }
          ]
        }
      }
    };
    const myChart = new Chart(id, config);
    return myChart;
  }

  getRandomGraph() {
    const randomScalingFactor = function() {
      return Math.round(Math.random() * 100);
    };
    const config = {
      type: 'bar',
      data: {
        datasets: [
          {
            data: [randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor(), randomScalingFactor()],
            backgroundColor: ['#2CD9E9', '#E4E62D', '#E8A32C', '#2DE897', '#DF3A27', '#3C68B1'],
            label: 'Dataset 1'
          }
        ],
        labels: ['Red', 'Orange', 'Yellow', 'Green', 'Blue']
      },
      options: {
        responsive: true
      }
    };
    const id = this.displayCommonData.pollId
      ? 'pollGraph2' + this.displayCommonData.pollId
      : 'feedGraph2' + this.displayCommonData.feedbackId;
    const myChart = new Chart(id, config);
    return myChart;
  }

  pageChangeEvent(event) {
    this.fetchResultsOnPageChange(event);
  }

  prepareAndOpenModal(content, data) {
    this.displayCommonData = data;
    const currentData = new CurrentModal();
    currentData.optionsRespons = [];
    currentData.options = [];
    currentData.optionType = data.pollType || data.feedbackType;

    if (data.questions && data.questions.length > 0) {
      data.questions.forEach((element, index) => {
        if (element.responses && element.responses.length > 0) {
          element.responses.forEach(obj => {
            obj.optionNumber = index + 1;
            currentData.optionsRespons.push(obj);
          });
        }
        currentData.options.push(element);
      });
    }

    if (data.pollImages && data.pollImages.length > 0) {
      data.pollImages.forEach((element, optionIndex) => {
        if (element.imageRespons && element.imageRespons.length > 0) {
          element.imageRespons.forEach(obj => {
            obj.optionNumber = optionIndex + 1;
            currentData.optionsRespons.push(obj);
          });
        }
        currentData.options.push(element);
      });
    }

    if (currentData.optionType === 2 || currentData.optionType === 3) {
      currentData.rateCounts = this.getPieChartData(data);
    }

    if (data.ratings && data.ratings.length > 0) {
      data.ratings.forEach(element => {
        currentData.optionsRespons.push(element);
      });
    }

    if (data.freeComments && data.freeComments.length) {
      data.freeComments.forEach(element => {
        currentData.optionsRespons.push(element);
      });
    }

    this.displayListData = currentData;
    this.openDetailsViewModal(content, data);
  }

  openDetailsViewModal(content, data) {
    this.modalService
      .open(content, {
        windowClass: 'detailedReport',
        size: 'lg'
      })
      .result.then(
        result => {
        },
        error => {
        }
      );
    setTimeout(() => {
      const id = data.pollId ? 'pollGraph2' + data.pollId : 'feedGraph2' + data.feedbackId;
      this.getBarChart(id, data);
    }, 0);
  }
}
