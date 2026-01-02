# Kubernetes Spring Boot Microservices
## Stateless & Stateful Architecture – Real-World Hands-On Project

---

## Purpose of This Repository

This repository contains a **production-style Kubernetes project** built to deeply understand:

- Stateless vs Stateful workloads
- Kubernetes Deployments, StatefulSets, Jobs
- Persistent storage (PV / PVC)
- Zero-downtime rolling deployments
- Configuration management (ConfigMaps & Secrets)
- Real production failures and debugging
- Kubernetes vs OpenShift differences

This is **not a tutorial demo**.  
It is a **hands-on learning and interview-preparation project**.

---

## High-Level Architecture

Client  
→ Ingress  
→ Order Service (Spring Boot – Stateless)  
→ Inventory Service (Spring Boot – Stateless)  
→ PostgreSQL (StatefulSet + PVC)

---

## Microservices Overview

### Order Service
**Type:** Stateless Spring Boot microservice  
**Role:** Public-facing business API

**Responsibilities**
- Accept order requests
- Call Inventory Service
- Persist order data to PostgreSQL
- Expose health endpoints

**Kubernetes Concepts Used**
- Deployment
- RollingUpdate (`maxUnavailable: 0`)
- Service (ClusterIP)
- Ingress
- ConfigMap & Secret
- Readiness & Liveness probes

---

### Inventory Service
**Type:** Stateless Spring Boot microservice  
**Role:** Internal dependency (not exposed externally)

**Responsibilities**
- Provide inventory data
- Demonstrate east-west service communication

**Kubernetes Concepts Used**
- Deployment
- Service (ClusterIP)
- DNS-based service discovery

---

### PostgreSQL Database
**Type:** Stateful workload  
**Role:** Persistent data storage

**Kubernetes Concepts Used**
- StatefulSet
- PersistentVolumeClaim (PVC)
- Headless Service
- Stable pod identity (`postgres-0`)
- Data persistence across pod restarts

---

## Database Initialization Strategy

### Problem
Initializing database schema during application startup:
- Breaks rolling deployments
- Causes CrashLoopBackOff
- Couples application lifecycle to database availability

### Solution
- Database schema initialized using a **Kubernetes Job**
- Job runs independently of the application
- Job is idempotent and safe to re-run
- Application starts even if DB schema is not yet present

---

## Kubernetes Objects Used

- Namespace
- Deployment
- StatefulSet
- Service (ClusterIP)
- Headless Service
- Ingress
- ConfigMap
- Secret
- Job
- PersistentVolumeClaim (PVC)

---

## Real Issues Faced & Lessons Learned

- CrashLoopBackOff due to duplicate YAML keys
- Rollout blocked by missing environment variables
- Old pods serving traffic while new pods failed
- Database Job failure due to missing `PGPASSWORD`
- Importance of explicit ConfigMap & Secret injection

---

## Environment Used

- OS: Ubuntu (WSL2)
- Kubernetes: kind
- Container Runtime: Docker Desktop
- Java: 17
- Build Tool: Maven
- Database: PostgreSQL 15

---

## High-Level Deployment Steps

1. Create namespace
2. Deploy PostgreSQL (StatefulSet + PVC)
3. Run DB init Job
4. Deploy Inventory Service
5. Deploy Order Service
6. Access API via Ingress

---

## Configuration Management

- **ConfigMaps**
  - Application configuration
  - Database host, port, name

- **Secrets**
  - Database username & password
  - Explicitly injected into pods

> Kubernetes does not auto-wire configuration.  
> Pods only see what is explicitly injected.

---

## Storage & Persistence

- PostgreSQL uses PVC-backed storage
- Data survives:
  - Pod deletion
  - Pod restart
  - Node rescheduling

---

## Kubernetes vs OpenShift (Summary)

Kubernetes provides orchestration primitives.  
OpenShift adds:
- Enforced security (SCC)
- Routes instead of Ingress
- Built-in registry
- Integrated CI/CD

This project can be migrated to OpenShift with minimal changes.

---

## Interview-Ready Summary

Built two stateless Spring Boot microservices and a PostgreSQL StatefulSet on Kubernetes. Implemented zero-downtime rolling deployments, persistent storage using PVCs, Job-based database initialization, and externalized configuration using ConfigMaps and Secrets. Debugged real rollout and startup failures using production-grade patterns.

---

## Why This Repository Exists

- Long-term revision reference
- Real Kubernetes failure handling
- Platform engineering mindset
- DevOps / SRE interview preparation

---

## Future Enhancements

- CI/CD pipeline
- Autoscaling (HPA)
- Resilience patterns
- Observability
- Security hardening

---

